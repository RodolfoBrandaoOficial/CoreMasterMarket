package com.rodolfobrandao.coremastermarket.entities.pdv;

import static org.junit.jupiter.api.Assertions.*;

class MarcaTest {

    @org.junit.jupiter.api.Test
    void test() {

      Marca marca = new Marca();
      marca.setId(1L);
      marca.setNome("NESCAL");
      marca.setFabricante("NESTLE");
      assertEquals(1L, marca.getId());
      assertEquals("NESCAL", marca.getNome());
      assertEquals("NESTLE", marca.getFabricante());
    }
}
