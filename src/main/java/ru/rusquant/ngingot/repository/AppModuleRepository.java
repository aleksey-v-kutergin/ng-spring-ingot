package ru.rusquant.ngingot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rusquant.ngingot.domain.AppModule;

public interface AppModuleRepository extends JpaRepository<AppModule, Long> {

}
