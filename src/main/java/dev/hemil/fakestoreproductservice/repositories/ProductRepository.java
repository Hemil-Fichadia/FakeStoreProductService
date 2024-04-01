package dev.hemil.fakestoreproductservice.repositories;

import dev.hemil.fakestoreproductservice.models.Product;
import dev.hemil.fakestoreproductservice.repositories.projections.ProductProjection;
import dev.hemil.fakestoreproductservice.repositories.projections.ProductWithIdAndTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /* These are JPA queries
    * */
    Product save(Product p);
    //The attributes that DB automatically generates will not be
    //in the params but will be there in the returned object

    @Override
    List<Product> findAll();

    Product findByIdIs(Long id);

    List<Product> findAllByCategory_Title(String category);

    void deleteById(Long id);

    @Override
    boolean existsById(Long id);

    //HQL stands for Hibernate Query Language
    /* These are HQL queries.
    The main advantage of using HQL queries is it makes the query database independent,
    and here we are working in terms of models instead of using table names directly we work
    in terms of models of the project.
    * */
    @Query("SELECT p from Product p where p.category.title = :title and p.id = :productId")
    Product getProductWithParticularName(@Param("title") String title,
                                             @Param("productId") Long productId);
    //HQL
    /* Here the projections we are using are solely dependent on the idea of receiving the objects
    and then put them back of.
    The data type is the projection of the typical just to get the two fields from data base.
    * */
    @Query("SELECT p.title AS title, p.id AS id FROM Product p WHERE p.category.id = :categoryId")
    List<ProductWithIdAndTitle> getTitlesOfProductsOfGivenCategory(@Param("categoryId") Long categoryId);
    //HQL
    @Query("SELECT p.id AS id, p.title AS title, p.description AS description FROM Product p WHERE p.id = :productId")
    List<ProductProjection> getProductDetailsOfGivenId(@Param("productId") Long productId);


    //SQL

}
