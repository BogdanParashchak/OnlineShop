package com.parashchak.onlineshop.presentation;

import com.parashchak.onlineshop.entity.Product;
import org.junit.jupiter.api.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PageGeneratorITest {

    private File templateWithProductDataFile;
    private File templateWithoutProductDataFile;
    private final static String TEMPLATE_WITH_PRODUCT_DATA =
            "<table>\n" +
                    "    <#list products as product>\n" +
                    "    <tr>\n" +
                    "        <td>${product.id}</td>\n" +
                    "        <td>${product.name}</td>\n" +
                    "        <td>${product.price}</td>\n" +
                    "    </tr>\n" +
                    "</#list>\n" +
                    "</table>";

    private final static String TEMPLATE_WITHOUT_PRODUCT_DATA =
            "<table>\n" +
                    "    <tr>\n" +
                    "        <td>1</td>\n" +
                    "        <td>2</td>\n" +
                    "        <td>3</td>\n" +
                    "    </tr>\n" +
                    "</table>";

    @BeforeEach
    void setUp() throws IOException {
        templateWithProductDataFile = new File("src/templateWithProductData.html");
        templateWithProductDataFile.createNewFile();

        templateWithoutProductDataFile = new File("src/templateWithoutProductData.html");
        templateWithoutProductDataFile.createNewFile();

        char[] templateChars = TEMPLATE_WITH_PRODUCT_DATA.toCharArray();
        try (BufferedWriter bufferedWriter =
                     new BufferedWriter(new FileWriter(templateWithProductDataFile))) {
            bufferedWriter.write(templateChars);
        }

        templateChars = TEMPLATE_WITHOUT_PRODUCT_DATA.toCharArray();
        try (BufferedWriter bufferedWriter =
                     new BufferedWriter(new FileWriter(templateWithoutProductDataFile));) {
            bufferedWriter.write(templateChars);
        }
    }

    @AfterEach
    void tearDown() {
        try {
            if (templateWithProductDataFile.exists()) {
                templateWithProductDataFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (templateWithoutProductDataFile.exists()) {
                    templateWithoutProductDataFile.delete();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Test
    @DisplayName("generate page from template with fields for page data")
    void givenTemplateWithFieldsForPageDataAndPageData_whenGetPage_thenPageGenerated() {
        //prepare
        Product firstProduct = new Product();
        firstProduct.setId(1);
        firstProduct.setName("firstProduct");
        firstProduct.setPrice(10);

        Product secondProduct = new Product();
        secondProduct.setId(2);
        secondProduct.setName("secondProduct");
        secondProduct.setPrice(20);

        List<Product> products = List.of(firstProduct, secondProduct);
        Map<String, Object> pageData = Map.of("products", products);

        String expectedPage =
                "<table>\n" +
                        "        <tr>\n" +
                        "        <td>1</td>\n" +
                        "        <td>firstProduct</td>\n" +
                        "        <td>10</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td>2</td>\n" +
                        "        <td>secondProduct</td>\n" +
                        "        <td>20</td>\n" +
                        "    </tr>\n" +
                        "</table>";

        PageGenerator pageGenerator = PageGenerator.instance();
        pageGenerator.setHtmlTemplatesPath("src");

        //when
        String actualPage = pageGenerator.getPage("templateWithProductData.html", pageData);

        //then
        assertEquals(expectedPage, actualPage);
    }

    @Test
    @DisplayName("generate page from template without fields for page data")
    void givenTemplateWithoutFieldsForPageDataAndPageData_whenGetPage_thenPageGenerated() {
        //prepare
        String expectedPage =
                        "<table>\n" +
                        "    <tr>\n" +
                        "        <td>1</td>\n" +
                        "        <td>2</td>\n" +
                        "        <td>3</td>\n" +
                        "    </tr>\n" +
                        "</table>";

        PageGenerator pageGenerator = PageGenerator.instance();
        pageGenerator.setHtmlTemplatesPath("src");

        //when
        String actualPage = pageGenerator.getPage("templateWithoutProductData.html");

        //then
        assertEquals(expectedPage, actualPage);
    }

    @Test
    @DisplayName("when generate page from template with fields for page data and Null page data then RuntimeException thrown")
    void givenTemplateWithFieldsForPageDataAndNullPageData_whenGetPage_thenRuntimeExceptionThrown() {
        //prepare
        PageGenerator pageGenerator = PageGenerator.instance();
        pageGenerator.setHtmlTemplatesPath("src");

        //then
        Assertions.assertThrows(RuntimeException.class,
                () ->  pageGenerator.getPage("templateWithProductData.html", null));
    }
}