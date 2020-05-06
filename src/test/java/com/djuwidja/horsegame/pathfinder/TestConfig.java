package com.djuwidja.horsegame.pathfinder;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.djuwidja.horsegame")
public class TestConfig {

	@Bean
	public DataSource testDataSource() {
		return DataSourceBuilder.create().url("jdbc:mysql://localhost:3306/horse_game")
								.username("root")
								.password("password")
								.build();
	}
}
