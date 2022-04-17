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

    private File templatesFolder;
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
        templatesFolder = new File("test");
        templatesFolder.mkdir();

        templateWithProductDataFile = new File("test/templateWithProductData.html");
        templateWithoutProductDataFile = new File("test/templateWithoutProductData.html");

        templateWithProductDataFile.createNewFile();
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
        templateWithProductDataFile.delete();
        templateWithoutProductDataFile.delete();
        templatesFolder.delete();
    }

    @Test
    @DisplayName("generate page from template with fields for page data")
    void givenTemplateWithFieldsForPageDataAndPageData_whenGetPage_thenPageGenerated() throws IOException {
        //prepare
        Product firstProduct = Product.builder()
                .id(1)
                .name("firstProduct")
                .price(10)
                .build();
        ;

        Product secondProduct = Product.builder()
                .id(2)
                .name("secondProduct")
                .price(20)
                .build();
        ;

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

        PageGenerator pageGenerator = PageGenerator.instance(templatesFolder.getPath());

        //when
        String actualPage = pageGenerator.getPage("templateWithProductData.html", pageData);

        //then
        assertEquals(expectedPage, actualPage);
    }

    @Test
    @DisplayName("generate page from template without fields for page data")
    void givenTemplateWithoutFieldsForPageDataAndPageData_whenGetPage_thenPageGenerated() throws IOException {
        //prepare
        String expectedPage =
                "<table>\n" +
                        "    <tr>\n" +
                        "        <td>1</td>\n" +
                        "        <td>2</td>\n" +
                        "        <td>3</td>\n" +
                        "    </tr>\n" +
                        "</table>";

        PageGenerator pageGenerator = PageGenerator.instance(templatesFolder.getPath());

        //when
        String actualPage = pageGenerator.getPage("templateWithoutProductData.html");

        //then
        assertEquals(expectedPage, actualPage);
    }

    @Test
    @DisplayName("when generate page from template with fields for page data and Null page data then RuntimeException thrown")
    void givenTemplateWithFieldsForPageDataAndNullPageData_whenGetPage_thenRuntimeExceptionThrown() throws IOException {
        //prepare
        PageGenerator pageGenerator = PageGenerator.instance(templatesFolder.getPath());

        //then
        Assertions.assertThrows(RuntimeException.class,
                () -> pageGenerator.getPage("templateWithProductData.html", null));
    }
}