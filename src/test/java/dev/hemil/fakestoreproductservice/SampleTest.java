package dev.hemil.fakestoreproductservice;

import dev.hemil.fakestoreproductservice.controllers.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* This annotation enables us to use the spring beans which are in
the project context.
*/
@SpringBootTest
public class SampleTest {
    //This autowired annotation will not work without @SpringBootTest annotation
    @Autowired
    private ProductController productController;
    /* A test case is just a method that is used to test the actual functionalities

    AAA
    A --> Arrange
    A --> Act
    Assert --> Checking against expected value
    */

    /* A method is a test method only if we annotate it with @Test annotation,
    and this annotation in springboot gives a special status of a method
    in terms of project context in Test package, and it also provides an option
    to be executed individually with that green play button.
    */
    @Test
    void testDemoFunctionality(){
        /* In a test case, we can have multiple assertions and it will
        only pass if all the assertions are passed.
        */
        //Arrange the parameter
        int x = 1;
        int y = 2;
        //Act upon the parameter
        x += 1;
        y *= 2;
        /*Assert, means to check if the test case is passing against the given
        value or not
        it simply means
        if(x == 2){
            pass
        }
        fail
        */
        assert x == 2;
        //This will fail
        //assert x == 20;
        assert y == 4;

        /* The Junit contains a class "asserEquals()" and there are lot of others like
        assertNotNull(), assertThrows() assertThrowsExactly() etc. so this will come
        around a handy way to test different types of conditions like if we are testing
        a particular type of method is throwing an exception, then we have to write
        some printing statements which can assure us if a method is called or not, but
         it's a pretty unoptimised way for testing as now we have support of such libraries.

         This assertEquals() is a better way to test as it also prints some related
         error messages like what was expected and what are we getting, so it provides
         better visibility.
        */
        /*Here the first parameter is expected value, and the second parameter is
        actual value means what are getting from the operation.
        */
        assertEquals(2, x);
        /* If there are multiple assertions failing, then the first failing will
        give the message.
        */
        assertEquals(4, y);


    }

}
