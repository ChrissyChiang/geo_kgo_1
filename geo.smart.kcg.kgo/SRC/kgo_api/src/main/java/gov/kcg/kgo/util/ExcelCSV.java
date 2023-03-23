package gov.kcg.kgo.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

public class ExcelCSV {

	/**
	 * ���o���w�C�Ϋ��w�檺��
	 * 
	 * @param path �ɮ׸��|
	 * @param row  �C
	 * @param col  ��
	 * @return
	 */
	public static String getField(String path, int row, int col) {

		String result = new String();

		try {
			return getField(new FileInputStream(path), row, col);// �ɮ�Ū�����|

		} catch (FileNotFoundException e) {
			System.out.print("[FileNotFoundException] Error ... \n" + e.getMessage());
		}

		return result;
	}

	public static Map<String, Object> getFieldRowByBase64Str(String fileBase64Str, int headerRow, int dataRow) {

		List<Map<String, Object>> dataList = getFieldDataByBase64Str(fileBase64Str, headerRow, dataRow, dataRow);
		return dataList.size() > 0 ? dataList.get(0) : new HashedMap<>();
	}

	public static List<Map<String, Object>> getFieldDataByBase64Str(String fileBase64Str, int headerRow, int dataStartRow) {
		return getFieldDataByBase64Str(fileBase64Str, headerRow, dataStartRow, 999999999);
	}

	public static List<Map<String, Object>> getFieldDataByBase64Str(String fileBase64Str, int headerRow, int dataStartRow, int dataEndRow) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		try {
			byte[] abyte = Base64.getDecoder().decode(fileBase64Str.getBytes());
			InputStream is = new ByteArrayInputStream(abyte);
			result = getFieldData(is, headerRow, dataStartRow, dataEndRow);// �ɮ�Ū�����|

		} catch (Exception e) {
			System.out.print("[FileNotFoundException] Error ... \n" + e.getMessage());
		}

		return result;
	}

	public static String getFieldByBase64Str(String fileBase64Str, int row, int col) {

		String result = new String();

		try {
			byte[] abyte = Base64.getDecoder().decode(fileBase64Str.getBytes());
			InputStream is = new ByteArrayInputStream(abyte);
			result = getField(is, row, col);// �ɮ�Ū�����|

		} catch (Exception e) {
			System.out.print("[FileNotFoundException] Error ... \n" + e.getMessage());
		}

		return result;
	}

	public static List<Map<String, Object>> getFieldData(InputStream is, int headerRow, int dataStartRow, int dataEndRow) {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		try {
			InputStreamReader isr = new InputStreamReader(is, "Big5");// �ɮ�Ū�����|
			BufferedReader reader = new BufferedReader(isr);

			String line = null;
			int index = 0;
			try {

				String header[] = null;
				boolean useHeader = false;
				while ((line = reader.readLine()) != null) {

					Map<String, Object> rowMap = new HashedMap<>();
					if (index == headerRow) {
						header = line.split(",");
						useHeader = true;

						// Header 行號比資料還後面
						if (headerRow > dataStartRow && result.size() > 0) {
							for (Map<String, Object> item : result) {
								for (int col = 0; col < item.size(); col++) {
									if (header.length > col) {
										Object obj = item.remove(Integer.toString(col));
										item.put(header[col], obj);
									}
								}
							}
						}
					}

					if (index >= dataStartRow && index <= dataEndRow) {
						String item[] = line.split(",");
						for (int col = 0; col < item.length; col++) {
							if (useHeader && header.length > col) {
								rowMap.put(header[col], item[col]);
							} else {
								rowMap.put(Integer.toString(col), item[col]);
							}
						}

						result.add(rowMap);
					}
					if (index >= dataEndRow && useHeader) {
						break;
					}
					index++;
				}

				reader.close();
				isr.close();

			} catch (IOException e) {
				System.out.print("[IOException] Error ... \n" + e.getMessage());
			}
		} catch (Exception e) {
			System.out.print("[FileNotFoundException] Error ... \n" + e.getMessage());
		}

		return result;
	}

	public static String getField(InputStream is, int row, int col) {

		String result = new String();

		try {
			InputStreamReader isr = new InputStreamReader(is, "Big5");// �ɮ�Ū�����|
			BufferedReader reader = new BufferedReader(isr);

			String line = null;
			int index = 0;
			try {

				while ((line = reader.readLine()) != null) {
					// ���o��row�C�����
					if (index == row) {
						String item[] = line.split(",");
						// ���o�ӦC��col���
						result = item[col];
						isr.close();
						reader.close();
						break;
					} else {
						index++;
					}
				}
			} catch (IOException e) {
				System.out.print("[IOException] Error ... \n" + e.getMessage());
			}
		} catch (Exception e) {
			System.out.print("[FileNotFoundException] Error ... \n" + e.getMessage());
		}

		return result;
	}
}
