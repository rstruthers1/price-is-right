package com.myRetail.products.cassandra.domain;

import com.datastax.driver.core.DataType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Table("products")
@Getter
@Setter
@NoArgsConstructor
public class Product implements Serializable{

    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    private UUID id;
    private String description;
    private BigDecimal price;
    private String imageUrl;
}
