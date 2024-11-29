import com.sharcar.domain.usecases.enterprise.CreateEnterprise
import org.koin.dsl.module

val enterpriseModule = module {
    includes(enterpriseRepositoryModule)

    single { CreateEnterprise(get()) }
}