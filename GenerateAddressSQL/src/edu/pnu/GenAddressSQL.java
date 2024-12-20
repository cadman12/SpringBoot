package edu.pnu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GenAddressSQL {
	
	private static String tablename = "address";
	private Set<Integer> hash = new HashSet<>(); 

	public static void main(String[] args) {

		GenAddressSQL ga = new GenAddressSQL();
		
		try(BufferedReader br = new BufferedReader(new FileReader("부산광역시.txt"));
			BufferedWriter bw = new BufferedWriter(new FileWriter(tablename+".sql"));) {
			ga.writerHeader(bw, tablename);
			
			String line = br.readLine();	// title;
			
			int cnt = 0;
			while(true) {
				line = br.readLine();
				if (line == null)	break;

				ga.writeSQL(bw, line);
				
				System.out.println(cnt++);
			}
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		System.out.println("Done!");
	}

	//우편번호|시도|시도영문|시군구|시군구영문|읍면|읍면영문|도로명코드|도로명|도로명영문|지하여부|건물번호본번|건물번호부번|건물관리번호|다량배달처명|시군구용건물명|법정동코드|법정동명|리명|행정동명|산여부|지번본번|읍면동일련번호|지번부번|구우편번호|우편번호일련번호
	//46706|부산광역시|Busan|강서구|Gangseo-gu|||264404208490|제도로1041번길|Jedo-ro 1041beon-gil|0|137|33|2644010300110030000000001|||2644010300|강동동||강동동|0|1003|01|0||
	//46706|부산광역시|Busan|강서구|Gangseo-gu|||264404208490|제도로1041번길|Jedo-ro 1041beon-gil|0|172|0|2644010300108420000000001|||2644010300|강동동||강동동|0|842|01|0||

	// (46706,'부산광역시','Busan','강서구','Gangseo-gu','','','264404208490 ','제도로1041번길','Jedo-ro 1041beon-gil',0,137,33,'2644010300110030000000000 ','','','2644010300 ','강동동','','강동동',0,1003,1,0,'',''),(46706,'부산광역시','Busan','강서구','Gangseo-gu','','','264404208490 ','제도로1041번길','Jedo-ro 1041beon-gil',0,172,0,'2644010300108420000000000 ','','','2644010300 ','강동동','','강동동',0,842,1,0,'','')
	private void writeSQL(BufferedWriter bw, String line) throws IOException {
		
		String[] arr = line.split("\\|");
		ArrayList<String> arr1 = new ArrayList<>(Arrays.asList(arr));
		
		int len = 26 - arr1.size();
		for(int i = 0 ; i < len ; i++) {
			arr1.add("");
		}
		
		bw.write("INSERT INTO `" + tablename + "` VALUES(");
		
		for(int i = 0 ; i < arr1.size(); i++) {
			if (!hash.contains(i)) {
				bw.write("'");
			}
			
			String s = arr1.get(i);
			s = s.replaceAll("'", "");
			
			bw.write(s);
			
			if (!hash.contains(i)) {
				bw.write("'");
			}
			
			if (i + 1 == arr1.size())	bw.write(");\n");
			else						bw.write(",");
		}
	}
	
	private void writerHeader(BufferedWriter bw, String tablename) throws IOException {
		bw.write("DROP TABLE IF EXISTS `" + tablename + "`;\n");
		bw.write("CREATE TABLE `" + tablename + "` (\n");
		bw.write("  `우편번호` int DEFAULT NULL,\n");			hash.add(0);
		bw.write("  `시도` text,\n");
		bw.write("  `시도영문` text,\n");
		bw.write("  `시군구` text,\n");
		bw.write("  `시군구영문` text,\n");
		bw.write("  `읍면` text,\n");
		bw.write("  `읍면영문` text,\n");
		bw.write("  `도로명코드` text,\n");
		bw.write("  `도로명` text,\n");
		bw.write("  `도로명영문` text,\n");
		bw.write("  `지하여부` int DEFAULT NULL,\n");			hash.add(10);
		bw.write("  `건물번호본번` int DEFAULT NULL,\n");		hash.add(11);
		bw.write("  `건물번호부번` int DEFAULT NULL,\n");		hash.add(12);
		bw.write("  `건물관리번호` text,\n");
		bw.write("  `다량배달처명` text,\n");
		bw.write("  `시군구용건물명` text,\n");
		bw.write("  `법정동코드` text,\n");
		bw.write("  `법정동명` text,\n");
		bw.write("  `리명` text,\n");
		bw.write("  `행정동명` text,\n");
		bw.write("  `산여부` int DEFAULT NULL,\n");			hash.add(20);
		bw.write("  `지번본번` int DEFAULT NULL,\n");			hash.add(21);
		bw.write("  `읍면동일련번호` int DEFAULT NULL,\n");		hash.add(22);
		bw.write("  `지번부번` int DEFAULT NULL,\n");			hash.add(23);
		bw.write("  `구우편번호` text,\n");
		bw.write("  `우편번호일련번호` text\n");
		bw.write(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n");
	}
}
