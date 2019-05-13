package tk.mybatis.springboot.common.util;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.springboot.modules.customer.model.Customer;
import tk.mybatis.springboot.modules.customer.service.CustomerService;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Excel工具类
 */
@Component
public class ExcelUtil {
    @Autowired
    private CustomerService customerService;

    /**
     * 生成excel文件 在D:\
     * 1生成登陆日志
     * 2生成操作日志
     *
     * @param logType
     * @return
     */
    public File getExcel(int logType) {
        /*List<ShowLog> list = null;
        if (logType == 1) {
            list = loginLogService.getAllShowLoginLog();
        }else if(logType == 2){
            list = operationLogService.getAllShowLog();
        }*/
        List<Customer> list = customerService.findAll();

        File file = new File("d:\\customer" + System.currentTimeMillis() + ".xls");
        WritableWorkbook workbook = null;
        try {
            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("sheet1", 0);
            WritableCellFormat wcf = new WritableCellFormat();
            wcf.setBackground(Colour.YELLOW);
            sheet.addCell(new Label(0, 0, "客户名称", wcf));
            sheet.addCell(new Label(1, 0, "地区", wcf));
            sheet.addCell(new Label(2, 0, "客户等级", wcf));
            sheet.addCell(new Label(3, 0, "客户满意度", wcf));
            sheet.addCell(new Label(4, 0, "客户信用度", wcf));
            sheet.addCell(new Label(5, 0, "联系方式", wcf));
            sheet.addCell(new Label(6, 0, "客户传真", wcf));
            sheet.setColumnView(0, 20);
            sheet.setColumnView(1, 20);
            sheet.setColumnView(2, 30);
            sheet.setColumnView(3, 20);
            sheet.setColumnView(4, 20);
            sheet.setColumnView(5, 30);
            sheet.setColumnView(6, 30);
            for (int row = 0; row < list.size(); row++) {
                sheet.addCell(new Label(0, row + 1, list.get(row).getName()));
                sheet.addCell(new Label(1, row + 1, list.get(row).getRegion()));
                sheet.addCell(new Label(2, row + 1, list.get(row).getLevel()));
                sheet.addCell(new Label(3, row + 1, list.get(row).getSatify()));
                sheet.addCell(new Label(4, row + 1, list.get(row).getCredit()));
                sheet.addCell(new Label(5, row + 1, list.get(row).getTel()));
                sheet.addCell(new Label(6, row + 1, list.get(row).getFax()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.write();
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }

        return file;

    }

}