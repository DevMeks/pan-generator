package com.devmeks.pangenerator.container;

import org.testcontainers.containers.PostgreSQLContainer;

public class PanRepoPostgresqlContainer extends PostgreSQLContainer<PanRepoPostgresqlContainer> {

  private static final String IMAGE_VERSION = "postgres:11.1";
  private static PanRepoPostgresqlContainer container;

  private PanRepoPostgresqlContainer() {
    super(IMAGE_VERSION);
  }

  public static PanRepoPostgresqlContainer getInstance() {
    if (container == null) {
      container = new PanRepoPostgresqlContainer();
    }
    return container;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty("DB_URL", container.getJdbcUrl());
    System.setProperty("DB_USERNAME", container.getUsername());
    System.setProperty("DB_PASSWORD", container.getPassword());
  }

  @Override
  public void stop() {
    //do nothing, JVM handles shut down
  }


}
