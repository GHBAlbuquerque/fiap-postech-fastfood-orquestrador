package com.fiap.fastfood.common.interfaces.datasources;

import com.fiap.fastfood.external.orm.OrquestrationORM;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;

@EnableScan
public interface OrquestrationRepository extends DynamoDBCrudRepository<OrquestrationORM, String> {

}
