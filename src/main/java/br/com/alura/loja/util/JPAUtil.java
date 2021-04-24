package br.com.alura.loja.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory FACTORY =
            //Criação do código que irá gerenciar a persistencia do banco de dados
            //Valor do parâmetro deve ser o mesmo passado em <persistence-unit> no arquivo xml
            Persistence.createEntityManagerFactory("loja");

    public static EntityManager getEntityManager(){
         return FACTORY.createEntityManager();
    }
}
