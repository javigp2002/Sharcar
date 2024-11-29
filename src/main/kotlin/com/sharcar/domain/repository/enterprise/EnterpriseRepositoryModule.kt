import com.sharcar.datasource.enterprise.enterpriseDatasourceModule
import com.sharcar.domain.repository.enterprise.EnterpriseRepository
import com.sharcar.domain.repository.enterprise.EnterpriseRepositoryImpl
import org.koin.dsl.module

val enterpriseRepositoryModule = module {
    includes(enterpriseDatasourceModule)

    single<EnterpriseRepository> { EnterpriseRepositoryImpl(get()) }
}