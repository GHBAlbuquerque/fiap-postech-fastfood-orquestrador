package com.fiap.fastfood.common.interfaces.datasources;

import com.fiap.fastfood.external.orm.OrquestrationRecordId;
import com.fiap.fastfood.external.orm.OrquestrationRecordORM;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface OrquestrationRepository extends CrudRepository<OrquestrationRecordORM, OrquestrationRecordId> {

}
