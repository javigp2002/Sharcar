import com.sharcar.domain.repository.user.UserRepositoryImpl
import org.koin.dsl.module

val userRepositoryModule = module {
    single { UserRepositoryImpl(get()) }
}