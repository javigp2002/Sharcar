import com.sharcar.datasource.user.userDatasourceModule
import com.sharcar.domain.repository.user.UserRepository
import com.sharcar.domain.repository.user.UserRepositoryImpl
import org.koin.dsl.module

val userRepositoryModule = module {
    includes(userDatasourceModule)

    single<UserRepository> { UserRepositoryImpl(get()) }
}