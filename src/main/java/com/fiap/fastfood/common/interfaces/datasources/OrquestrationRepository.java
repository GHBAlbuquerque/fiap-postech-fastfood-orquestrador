package com.fiap.fastfood.common.interfaces.datasources;

import com.fiap.fastfood.external.orm.OrquestrationRecordORM;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;

@EnableScan
public interface OrquestrationRepository extends DynamoDBCrudRepository<OrquestrationRecordORM, String> {

}
