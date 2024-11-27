import com.sharcar.domain.usecases.user.userUsecaseModule
import org.koin.dsl.module

val apiModule = module {
    includes(userUsecaseModule, travelModule)
}