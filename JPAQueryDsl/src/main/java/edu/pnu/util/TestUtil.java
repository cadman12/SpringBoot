package edu.pnu.util;

public class TestUtil {

	// Console 창에 수평 라인 그리는 메소드
	private static void drawHorLine(String rep) {	System.out.println(rep.repeat(80));	}
	public static void drawTitle(String title) {
		drawHorLine("=");
		System.out.println(title);
		drawHorLine("-");
	}
}
