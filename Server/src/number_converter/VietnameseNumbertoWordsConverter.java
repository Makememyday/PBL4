package number_converter;

public class VietnameseNumbertoWordsConverter extends NumberToWordsConverter
{	
	// Singleton
	private static VietnameseNumbertoWordsConverter instance;
	
	private VietnameseNumbertoWordsConverter() {}
	
	public static VietnameseNumbertoWordsConverter getInstance()
	{
		if (instance == null) {
            // Nếu thể hiện chưa tồn tại, tạo thể hiện mới
            instance = new VietnameseNumbertoWordsConverter();
        }
        return instance;
	}
	
	private final String[] VI_DIGITS = {
        "không", "một", "hai", "ba", "bốn",
        "năm", "sáu", "bảy", "tám", "chín"
	};
	
	@Override
	protected String[] getDigitLiterals() 
	{
		return VI_DIGITS;
	}
	
	// Trả về đơn vị tương ứng với vị trí index
	@Override
	protected String getUnit(int index) 
	{
		//  0     1        2       3     4        5       6          7
	    // "", "nghìn", "triệu", "tỉ", "nghìn", "triệu", "tỉ tỉ", "nghìn", ...
		String unit = "";
		
        if (index % 3 == 1) {
            unit = "nghìn";
        } else if (index % 3 == 2) {
            unit = "triệu";
        } else {
            int level = index / 3;
            StringBuilder strBuilder = new StringBuilder();
            for (int i = 1; i <= level; ++i) {
                strBuilder.append("tỉ ");
            }
            if (level > 0) {
                // Xóa đi một kí tự space thừa ở cuối xâu
                strBuilder.deleteCharAt(strBuilder.length() - 1);
            }

            unit = strBuilder.toString();
        }
        
        return unit;
	}

	@Override
	protected String readTwoDigit(int b, int c) 
	{
		String res = "";
		
        if (b == 1) {
        	// mười, mười một, mười hai, ..., mười lăm, mười sáu, ..., mười chín
            res += "mười";
            if (c != 0) {
                res += " ";
                if (c == 5) {
                	res += "lăm";
                } else {
                	res += VI_DIGITS[c];
                }
            }
        } else {
        	// hai mươi, ba mươi, ..., chín mươi
            res += VI_DIGITS[b] + " mươi";
            
            // hai muơi + ...
            if (c != 0) {
                res += " ";
                if (c == 1) {
                    res += "mốt";
                } else if (c == 5) {
                    res += "lăm";
                } else {
                    res += VI_DIGITS[c];
                }
            }
        }
        
        return res;
	}
	
	// Đọc số có 3 chữ số (a có thể bằng 0)
	@Override
	protected String readThreeDigit(int a, int b, int c) 
	{
		// TH a != 0 -> Can doc chu so hang tram a
	    // TH a == 0 -> Trc a se co mot chu so khac 0 nen 
		// 	 van doc chu so hang tram a -> "khong tram"
		String res = VI_DIGITS[a] + " trăm";
		
        if (b == 0) {
        	// TH b == 0
            // so n co dang a0c, nếu c != 0 --> Đọc "lẻ c"
            if (c != 0) res += " lẻ " + VI_DIGITS[c];
        } else {
        	// TH b != 0 --> Doc tiep so co hai chu so bc
            res += " " + readTwoDigit(b, c);
        }
        return res;
	}

}
