package com.fiap.fastfood.common.interfaces.datasources;

import com.fiap.fastfood.external.orm.OrquestrationORM;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface OrquestrationRepository extends CrudRepository<OrquestrationORM, String> {
    List<OrquestrationORM> findAll();

    List<OrquestrationORM> findAllByIdOrderByCreatedAt();
}
