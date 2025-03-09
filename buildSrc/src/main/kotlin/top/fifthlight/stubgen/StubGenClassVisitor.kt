package top.fifthlight.stubgen

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import kotlin.concurrent.withLock
import kotlin.math.min

class StubGenClassVisitor(
    private val classMap: ClassMap,
) : ClassVisitor(Opcodes.ASM9, null) {
    private var classItem: ClassMap.ClassItem? = null

    override fun visit(
        version: Int,
        access: Int,
        name: String,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        if (name == "module-info") {
            return
        }
        classMap.minClassVersion.accumulateAndGet(version, ::min)
        val classInfo = ClassMap.ClassInfo(
            name = name,
            access = access,
            superClass = superName,
            interfaces = interfaces?.toMutableSet() ?: mutableSetOf(),
            signature = signature,
        )
        classItem = classMap.classes.compute(name) { _, existingItem ->
            if (existingItem == null) {
                ClassMap.ClassItem(classInfo)
            } else {
                existingItem.lock.withLock {
                    if (existingItem.info.isCompatible(classInfo)) {
                        existingItem.info.mergeFrom(classInfo)
                        existingItem.visitCount++
                        existingItem
                    } else {
                        null
                    }
                }
            }
        }
    }

    override fun visitField(
        access: Int,
        name: String,
        descriptor: String,
        signature: String?,
        value: Any?,
    ): FieldVisitor? {
        classItem?.let { classItem ->
            val fieldItem = ClassMap.FieldItem(
                type = descriptor,
                access = access,
                staticValue = value,
                signature = signature,
            )
            classItem.lock.withLock {
                val existingItem = classItem.fields[name]
                if (existingItem != null) {
                    if (existingItem.isCompatible(fieldItem)) {
                        existingItem.visitCount++
                        existingItem.mergeFrom(fieldItem)
                    } else {
                        classItem.fields.remove(name)
                    }
                } else {
                    classItem.fields[name] = fieldItem
                }
            }
        }
        return null
    }

    override fun visitMethod(
        access: Int,
        name: String,
        descriptor: String,
        signature: String?,
        exceptions: Array<out String>?,
    ): MethodVisitor? {
        classItem?.let { classItem ->
            val methodIdentifier = ClassMap.MethodIdentifier(
                name = name,
                descriptor = descriptor,
            )
            val methodItem = ClassMap.MethodItem(
                access = access,
                signature = signature,
            )
            classItem.lock.withLock {
                val existingItem = classItem.methods[methodIdentifier]
                if (existingItem != null) {
                    if (existingItem.isCompatible(methodItem)) {
                        existingItem.visitCount++
                        existingItem.mergeFrom(methodItem)
                    } else {
                        classItem.methods.remove(methodIdentifier)
                    }
                } else {
                    classItem.methods[methodIdentifier] = methodItem
                }
            }
        }
        return null
    }
}
