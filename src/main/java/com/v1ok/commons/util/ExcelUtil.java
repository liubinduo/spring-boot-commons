//package com.v1ok.commons.util;
//
//
//import com.v1ok.commons.annotation.ExcelAttribute;
//import com.v1ok.commons.support.ResultObject;
//import java.io.BufferedInputStream;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.lang.reflect.Method;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.nio.charset.StandardCharsets;
//import java.text.DecimalFormat;
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import javax.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections.MapUtils;
//import org.apache.commons.io.FilenameUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.time.DateFormatUtils;
//import org.apache.commons.lang3.time.DateUtils;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellType;
//import org.apache.poi.ss.usermodel.DateUtil;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.web.multipart.MultipartFile;
//
///**
// * Created by Delong on 2017/11/6.
// */
//@Slf4j
//@Deprecated
//public final class ExcelUtil<T> {
//
//  private final Class<T> clazz;
//
//  public ExcelUtil(Class<T> clazz) {
//    this.clazz = clazz;
//  }
//
//  /**
//   * Excel 2007+格式
//   */
//  private static final String XLSX = "xlsx";
//
//  /**
//   * Excel 2003格式
//   */
//  private static final String XLS = "xls";
//
//  public ResultObject<List<T>> importExcel(MultipartFile file, int startRowNum) {
//    return importExcel(file, startRowNum, null);
//  }
//
//  public ResultObject<List<T>> importExcel(MultipartFile file, int startRowNum,
//      ICallBack<T> callBack) {
//
//    ResultObject<List<T>> result = new ResultObject<>();
//    result.setResult(false);
//
//    if (file == null) {
//
//      result.setErrorMsg("导入文件不能为空");
//
//      return result;
//    }
//
//    // 文件类型判断
//    String suffix = FilenameUtils.getExtension(file.getOriginalFilename());
//
//    if (!XLS.equals(suffix) && !XLSX.equals(suffix)) {
//
//      result.setErrorMsg("导入文件格式错误，仅支持excel xls与xlsx格式");
//
//      return result;
//    }
//
//    // 创建工作簿
//    Workbook workbook = ExcelUtil.createWorkbook(file);
//
//    if (workbook == null) {
//
//      result.setErrorMsg("导入表格异常");
//
//      return result;
//    }
//
//    return importExcel(workbook.getSheetAt(0), startRowNum, callBack);
//
//
//  }
//
//  public ResultObject<List<T>> importExcel(Sheet sheet, int startRowNum) {
//    return importExcel(sheet, startRowNum, null);
//  }
//
//  /**
//   * @param sheet 工作表
//   * @param startRowNum 开始行号
//   * @param callBack 回调
//   * @return 结果集
//   */
//  public ResultObject<List<T>> importExcel(Sheet sheet, int startRowNum, ICallBack<T> callBack) {
//
//    ResultObject<List<T>> result = new ResultObject<>();
//
//    int maxCollNum = 0;
//
//    List<T> list = new ArrayList<>();
//
//    try {
//
//      int lastRowNum = sheet.getPhysicalNumberOfRows();
//
//      if (lastRowNum <= 0) {
//        return null;
//      }
//
//      Method[] declaredMethods = clazz.getDeclaredMethods();
//
//      Map<Integer, Method> methodMap = new HashMap<>();
//
//      Map<Integer, String> extKey = null;
//
//      String extMethod = null;
//
//      int extStartNum = 0;
//
//      for (Method method : declaredMethods) {
//
//        // 如果是注解属性
//        if (method.isAnnotationPresent(ExcelAttribute.class)) {
//
//          ExcelAttribute attr = method.getAnnotation(ExcelAttribute.class);
//
//          // 扩展字段处理
//          if (attr.isExt()) {
//
//            extStartNum = attr.columnIndex();
//
//            extMethod = method.getName().replace("get", "set");
//            extKey = getExtKey(extStartNum, sheet.getRow(startRowNum - 1));
//
//            continue;
//          }
//          // 列号
//          int col = attr.columnIndex();
//
//          maxCollNum = Math.max(col, maxCollNum);
//          methodMap.put(col, method);
//
//        }
//      }
//
//      for (int rowNum = startRowNum; rowNum < lastRowNum; rowNum++) {
//
//        Row row = sheet.getRow(rowNum);
//
//        T entity = clazz.newInstance();
//
//        if (callBack != null) {
//          callBack.initValue(entity);
//        }
//
//        // 常规字段处理
//        for (int cellNum = 0; cellNum <= maxCollNum; cellNum++) {
//
//          Cell cell = row.getCell(cellNum);
//
//          String cellValue = getSafeCellValue(cell);
//
//          Method method = methodMap.get(cellNum);
//
//          if (method == null) {
//            continue;
//          }
//
//          ExcelAttribute annotation = method.getAnnotation(ExcelAttribute.class);
//
//          // 必填校验
//          if (annotation.required() && StringUtils.isEmpty(cellValue)) {
//
//            result.setResult(false);
//            result.setErrorMsg("导入失败：第" + rowNum + "行，第" + cellNum + "列，不能为空");
//
//            return result;
//          }
//
//          // 非必填为空跳过循环
//          if (StringUtils.isEmpty(cellValue)) {
//
//            continue;
//          }
//
//          Class<?> fieldType = method.getReturnType();
//
//          Method setMethod = clazz.getMethod(method.getName().replace("get", "set"), fieldType);
//
//          try {
//
//            if (String.class == fieldType) {
//              setMethod.invoke(entity, cellValue);
//            } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
//              setMethod.invoke(entity, Integer.valueOf(cellValue));
//            } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
//              setMethod.invoke(entity, Long.valueOf(cellValue));
//            } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
//              setMethod.invoke(entity, Double.valueOf(cellValue));
//            } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
//              setMethod.invoke(entity, Float.valueOf(cellValue));
//            } else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
//              setMethod.invoke(entity, Short.valueOf(cellValue));
//            } else if ((Boolean.TYPE == fieldType) || (Boolean.class == fieldType)) {
//              setMethod.invoke(entity, Boolean.valueOf(cellValue));
//            } else if (Date.class == fieldType) {
//              setMethod.invoke(entity, DateUtils.parseDate(cellValue, "yyyy-MM-dd"));
//            } else if (List.class == fieldType) {
//
//              Type genericReturnType = method.getGenericReturnType();
//
//              if (ParameterizedType.class.isAssignableFrom(genericReturnType.getClass())) {
//                for (Type type : ((ParameterizedType) genericReturnType).getActualTypeArguments()) {
//
//                  // List<Double> 处理
//                  if ((Double.TYPE == type) || Double.class == type) {
//
//                    List<Double> doubleList = new ArrayList<>();
//
//                    String[] arr = cellValue.split(",");
//
//                    for (String s : arr) {
//
//                      doubleList.add(Double.parseDouble(s));
//                    }
//
//                    setMethod.invoke(entity, doubleList);
//
//                  }
//
//                  // others
//
//                }
//              }
//
//
//            }
//
//          } catch (NumberFormatException e) {
//
//            result.setResult(false);
//            result.setErrorMsg("导入失败：第" + rowNum + "行，第" + cellNum + "列，格式错误，");
//
//            return result;
//
//          } catch (ParseException e) {
//
//            result.setResult(false);
//            result.setErrorMsg("导入失败：第" + rowNum + "行，第" + cellNum + "列，日期格式不正确，格式为：yyyy-MM-dd，");
//
//            return result;
//          }
//
//
//        }
//
//        // 扩展字段处理
//        if (MapUtils.isNotEmpty(extKey)) {
//
//          Map<String, Object> ext = getExt(extStartNum, row, extKey);
//          clazz.getMethod(extMethod, Map.class).invoke(entity, ext);
//        }
//
//        if (entity != null) {
//
//          list.add(entity);
//        }
//
//
//      }
//
//      result.setResult(true);
//      result.setDate(list);
//
//      return result;
//
//    } catch (Exception e) {
//
//      e.printStackTrace();
//
//      result.setResult(false);
//      result.setErrorMsg(e.getMessage());
//
//      return result;
//
//    }
//
//  }
//
//
//  public static Workbook createWorkbook(MultipartFile excel) {
//
//    if (excel == null) {
//
//      log.info("Import object is empty.");
//
//      return null;
//    }
//
//    Workbook workbook = null;
//
//    FileInputStream fis = null;
//
//    try {
//
//      fis = (FileInputStream) excel.getInputStream();
//
//      String ext = FilenameUtils.getExtension(excel.getOriginalFilename());
//
//      log.info("Import excel suffix is :" + ext);
//
//      if (XLSX.equals(ext)) {
//
//        workbook = new XSSFWorkbook(fis);
//      }
//
//      if (XLS.equals(ext)) {
//
//        workbook = new HSSFWorkbook(fis);
//      }
//
//      return workbook;
//
//    } catch (IOException e) {
//
//      e.printStackTrace();
//
//      return null;
//    } finally {
//      if (fis != null) {
//        try {
//          fis.close();
//        } catch (IOException e) {
//
//          e.printStackTrace();
//
//          return null;
//        }
//      }
//
//    }
//
//
//  }
//
//
//  /**
//   * 安全返回单元格值
//   *
//   * @param cell 单元格对象
//   * @return String
//   */
//  public static String getSafeCellValue(Cell cell) {
//
//    if (cell == null) {
//
//      return "";
//    }
//
//    CellType cellType = cell.getCellTypeEnum();
//
//    String cellValue = "";
//
//    // 公式类型暂时返回空串，后续根据需求变更
//    if (CellType.FORMULA == cellType) {
//
//      return "";
//    }
//
//    // 字符串类型
//    if (CellType.STRING == cellType) {
//
//      cellValue = cell.getStringCellValue().trim();
//
//      return StringUtils.isEmpty(cellValue) ? "" : cellValue;
//    }
//
//    if (CellType.BOOLEAN == cellType) {
//
//      return String.valueOf(cell.getBooleanCellValue());
//    }
//
//    if (CellType.NUMERIC == cellType) {
//
//      // 日期处理
//      if (DateUtil.isCellDateFormatted(cell)) {
//
//        return DateFormatUtils.format(cell.getDateCellValue(), "yyyy-MM-dd");
//      } else {
//
//        return new DecimalFormat("#.######").format(cell.getNumericCellValue());
//      }
//    }
//
//    return cellValue;
//
//  }
//
//  /**
//   * 获取导入表格扩展字段名
//   *
//   * @param extStartNum 扩展字段起始位置
//   * @param row 表头行
//   * @return Map
//   */
//  public static Map<Integer, String> getExtKey(int extStartNum, Row row) {
//
//    // 存储扩展字段表头
//    Map<Integer, String> thExt = new HashMap<>();
//
//    if (row.getLastCellNum() > extStartNum) {
//
//      for (int extNum = extStartNum; extNum < row.getLastCellNum(); extNum++) {
//
//        thExt.put(extNum, ExcelUtil.getSafeCellValue(row.getCell(extNum)));
//
//      }
//    }
//
//    return thExt;
//
//  }
//
//  /**
//   * 获取某一行扩展字段集合
//   *
//   * @param extStartNum 扩展字段起始行
//   * @param row 扩展字段所在行
//   * @param extKey 扩展字段KEY集合
//   * @return Map
//   */
//  public static Map<String, Object> getExt(int extStartNum, Row row, Map<Integer, String> extKey) {
//
//    Map<String, Object> ext = new HashMap<>();
//
//    int lastNum = extStartNum + extKey.size();
//
//    if (lastNum > extStartNum) {
//
//      for (int extNum = extStartNum; extNum < lastNum; extNum++) {
//
//        String key = extKey.get(extNum);
//
//        if (StringUtils.isNotEmpty(key)) {
//
//          ext.put(key, ExcelUtil.getSafeCellValue(row.getCell(extNum)));
//        }
//
//      }
//    }
//
//    return ext;
//
//  }
//
//  /**
//   * 导入前基本校验
//   *
//   * @param file 文件
//   * @return IRestResponse
//   */
//  public static IRestResponse importCheck(MultipartFile file) {
//
//    if (file == null) {
//
//      return RestResponse.builder().error(HeadCode.ERROR).message("导入文件不能为空");
//    }
//
//    // 文件类型判断
//    String suffix = FilenameUtils.getExtension(file.getOriginalFilename());
//
//    if (!XLS.equals(suffix) && !XLSX.equals(suffix)) {
//
//      return RestResponse.builder().error(HeadCode.ERROR).message("导入文件格式错误，仅支持excel xls与xlsx格式");
//    }
//
//    return RestResponse.builder().success(true);
//
//  }
//
//  /**
//   * 下载指定模板
//   *
//   * @param res HttpServletResponse
//   * @param path 路径 例如：customerInfo.xls
//   * @param fileName 指定导出模板文件名，null时 等于path
//   */
//  public static void getTemplate(HttpServletResponse res, String path, String fileName) {
//
//    if (StringUtils.isEmpty(fileName)) {
//
//      fileName = path;
//    }
//
//    res.setHeader("content-type", "application/octet-stream");
//    res.setContentType("application/octet-stream");
//
//    byte[] buff = new byte[1024];
//    BufferedInputStream bis = null;
//    OutputStream os;
//
//    try {
//
//      res.setHeader("Content-Disposition",
//          "attachment;filename=" + new String(fileName.getBytes("GB2312"),
//              StandardCharsets.ISO_8859_1));
//
//      os = res.getOutputStream();
//
//      bis = new BufferedInputStream(ClassLoader.getSystemResourceAsStream(path));
//
//      int i = bis.read(buff);
//
//      while (i != -1) {
//        os.write(buff, 0, buff.length);
//        os.flush();
//        i = bis.read(buff);
//      }
//
//    } catch (IOException e) {
//
//      e.printStackTrace();
//
//    } finally {
//
//      if (bis != null) {
//
//        try {
//          bis.close();
//        } catch (IOException e) {
//          e.printStackTrace();
//        }
//
//      }
//    }
//
//  }
//
//
//  public interface ICallBack<T> {
//
//    void initValue(T entity);
//  }
//
//
//}
