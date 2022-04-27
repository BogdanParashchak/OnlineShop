package com.parashchak.onlineshop.presentation;

import com.parashchak.onlineshop.entity.Product;
import com.parashchak.onlineshop.web.presentation.PageGenerator;
import org.junit.jupiter.api.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PageGeneratorITest {

    private final static String TEMPLATE_WITH_PRODUCT_DATA =
            "<table>" +
                    "    <#list products as product>" +
                    "    <tr>" +
                    "        <td>${product.id}</td>" +
                    "        <td>${product.name}</td>" +
                    "        <td>${product.price}</td>" +
                    "    </tr>" +
                    "</#list>" +
                    "</table>";

    private final static String TEMPLATE_WITHOUT_PRODUCT_DATA =
            "<table>" +
                    "    <tr>" +
                    "        <td>1</td>" +
                    "        <td>2</td>" +
                    "        <td>3</td>" +
                    "    </tr>" +
                    "</table>";

    private File templatesFolder;
    private File templateWithProductDataFile;
    private File templateWithoutProductDataFile;

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
                "<table>" +
                        "        <tr>" +
                        "        <td>1</td>" +
                        "        <td>firstProduct</td>" +
                        "        <td>10</td>" +
                        "    </tr>" +
                        "    <tr>" +
                        "        <td>2</td>" +
                        "        <td>secondProduct</td>" +
                        "        <td>20</td>" +
                        "    </tr>" +
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
                "<table>" +
                        "    <tr>" +
                        "        <td>1</td>" +
                        "        <td>2</td>" +
                        "        <td>3</td>" +
                        "    </tr>" +
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