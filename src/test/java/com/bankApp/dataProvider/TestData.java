package com.bankApp.dataProvider;


import com.bankApp.base.BaseClass;
import com.bankApp.util.ExcelUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

import java.util.HashMap;
import java.util.Map;

public class TestData {


    private static final Logger logger = LogManager.getLogger(TestData.class);

    //sheet = 'InvalidLoginTestData' --> this sheet has testdata for invalid login
    @DataProvider(name = "fetchInvalidLoginTestData")
    public static Object[][] fetchInvalidLoginTestData() {

        return readProductDataFromExcel("InvalidLoginTestData");
    }

    //method will read data from excel with sheetname (dynamic headers added here)
    private static Object[][] readProductDataFromExcel(String sheetName) {
        ExcelUtils excelUtils = new ExcelUtils(BaseClass.testDataExcelPath, sheetName);
        int rowCount = excelUtils.getRowCount();
        //number of columns in header row -- to know
        int colCount = excelUtils.getSheet().getRow(0).getPhysicalNumberOfCells();

        //get headers from first row
        Map<Integer, String> headers = new HashMap<>();
        for (int col = 0; col < colCount; col++) {
            headers.put(col, excelUtils.getCellData(0, col));
        }

        //prepare data row .. skip header row
        Object[][] data = new Object[rowCount - 1][1];

        for (int i = 1; i < rowCount; i++) { // Start from row 1 to skip header
            Map<String, String> productData = new HashMap<>();
            for (int j = 0; j < colCount; j++) {
                productData.put(headers.get(j), excelUtils.getCellData(i, j));
            }
            data[i - 1][0] = productData;

            logger.info("Row {} data: {}", i, productData);
        }

        excelUtils.closeWorkbook();// close workbook
        return data;
    }

}
