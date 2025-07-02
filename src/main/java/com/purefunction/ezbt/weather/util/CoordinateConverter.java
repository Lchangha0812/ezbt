package com.purefunction.ezbt.weather.util;

public class CoordinateConverter {

    // 기상청 격자 좌표 변환 로직 (예시)
    // 실제 구현 시에는 기상청 가이드에 있는 변환 공식을 사용해야 합니다.
    public static Point convertLatLngToGrid(double lat, double lon) {
        // 여기서는 간단한 예시를 위해 더미 값을 반환합니다.
        // 실제로는 복잡한 변환 알고리즘이 들어갑니다.
        int nx = (int) (lon * 1.0);
        int ny = (int) (lat * 1.0);
        return new Point(nx, ny);
    }
}
