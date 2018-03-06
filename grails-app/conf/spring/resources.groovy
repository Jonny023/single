import com.custom.ConcurrentSingleSessionAuthenticationStrategy
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy
import org.springframework.security.web.session.ConcurrentSessionFilter

// Place your Spring DSL code here
beans = {

    sessionRegistry(SessionRegistryImpl)
    //很重要
    sessionFixationProtectionStrategy(SessionFixationProtectionStrategy){
        migrateSessionAttributes = true
        alwaysCreateSession = true
    }
    // "/login/already"为重定向请求
    concurrentSingleSessionAuthenticationStrategy(ConcurrentSingleSessionAuthenticationStrategy,ref('sessionRegistry'))
    registerSessionAuthenticationStrategy(RegisterSessionAuthenticationStrategy,ref('sessionRegistry'))
    sessionAuthenticationStrategy(CompositeSessionAuthenticationStrategy,[ref('concurrentSingleSessionAuthenticationStrategy'), ref('sessionFixationProtectionStrategy'), ref('registerSessionAuthenticationStrategy')])
    concurrentSessionFilter(ConcurrentSessionFilter, ref('sessionRegistry'), "/login/already")
}