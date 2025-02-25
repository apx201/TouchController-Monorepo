package top.fifthlight.touchcontroller.di

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import top.fifthlight.touchcontroller.about.AboutInfoProvider
import top.fifthlight.touchcontroller.about.ResourcesAboutInfoProvider
import top.fifthlight.touchcontroller.config.GlobalConfigHolder
import top.fifthlight.touchcontroller.config.preset.PresetManager
import top.fifthlight.touchcontroller.model.ControllerHudModel
import top.fifthlight.touchcontroller.model.TouchStateModel
import top.fifthlight.touchcontroller.ui.model.AboutScreenModel
import top.fifthlight.touchcontroller.ui.model.ComponentScreenModel
import top.fifthlight.touchcontroller.ui.model.CustomControlLayoutTabModel
import top.fifthlight.touchcontroller.ui.model.ItemListScreenModel
import top.fifthlight.touchcontroller.ui.model.ManageControlPresetsTabModel

val appModule = module {
    single {
        @OptIn(ExperimentalSerializationApi::class)
        Json {
            encodeDefaults = false
            ignoreUnknownKeys = true
            allowTrailingComma = true
            prettyPrint = true
            prettyPrintIndent = "  "
            isLenient = true
        }
    }
    single { GlobalConfigHolder() }
    single { PresetManager() }
    single { ControllerHudModel() }
    single { TouchStateModel() }
    single<AboutInfoProvider> { ResourcesAboutInfoProvider }

    factory<ItemListScreenModel> { params -> ItemListScreenModel(params[0], params[1]) }
    factory<ComponentScreenModel> { params -> ComponentScreenModel(params[0], params[1]) }
    factory { AboutScreenModel() }
    factory { ManageControlPresetsTabModel() }
    factory { CustomControlLayoutTabModel() }
}