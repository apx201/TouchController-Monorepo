package top.fifthlight.authproxy.mixin;

import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.logging.LogUtils;
import net.minecraft.server.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Mixin(Main.class)
public abstract class MainMixin {
    @Redirect(
            method = "main",
            at = @At(
                    value = "NEW",
                    target = "Lcom/mojang/authlib/yggdrasil/YggdrasilAuthenticationService;"
            )
    )
    private static YggdrasilAuthenticationService createAuthenticationService(Proxy proxy) {
        var logger = LogUtils.getLogger();

        var socksProxyHost = System.getProperty("socksProxyHost");
        var socksProxyPort = System.getProperty("socksProxyPort");
        var httpProxyHost = System.getProperty("http.proxyHost");
        var httpProxyPort = System.getProperty("http.proxyPort");
        var httpsProxyHost = System.getProperty("https.proxyHost");
        var httpsProxyPort = System.getProperty("https.proxyPort");

        // First, try SOCKS5
        if (socksProxyHost != null && socksProxyPort != null) {
            try {
                var port = Integer.parseInt(socksProxyPort);
                logger.info("Use SOCKS proxy: {}:{}", socksProxyHost, port);
                return new YggdrasilAuthenticationService(
                        new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(socksProxyHost, port))
                );
            } catch (NumberFormatException e) {
                logger.warn("Invalid SOCKS proxy port: {}", socksProxyPort);
            }
        }

        // Second try HTTPS
        if (httpsProxyHost != null && httpsProxyPort != null) {
            try {
                var port = Integer.parseInt(httpsProxyPort);
                logger.info("Use HTTPS proxy: {}:{}", httpsProxyHost, port);
                return new YggdrasilAuthenticationService(
                        new Proxy(Proxy.Type.HTTP, new InetSocketAddress(httpsProxyHost, port))
                );
            } catch (NumberFormatException e) {
                logger.warn("Invalid HTTPS proxy port: {}", httpsProxyPort);
            }
        }

        // Third try HTTP
        if (httpProxyHost != null && httpProxyPort != null) {
            try {
                var port = Integer.parseInt(httpProxyPort);
                logger.info("Use HTTP proxy: {}:{}", httpProxyHost, port);
                return new YggdrasilAuthenticationService(
                        new Proxy(Proxy.Type.HTTP, new InetSocketAddress(httpProxyHost, port))
                );
            } catch (NumberFormatException e) {
                logger.warn("Invalid HTTP proxy port: {}", httpProxyPort);
            }
        }

        // Fallback to no proxy
        logger.info("No proxy used");
        return new YggdrasilAuthenticationService(Proxy.NO_PROXY);
    }
}
