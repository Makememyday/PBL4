package number_converter;

public class EnglishNumberToWordsConverter extends NumberToWordsConverter 
{
	// Singleton
	private static EnglishNumberToWordsConverter instance;
	
	private EnglishNumberToWordsConverter() {}
	
	public static EnglishNumberToWordsConverter getInstance()
	{
		if (instance == null) {
            instance = new EnglishNumberToWordsConverter();
        }
        return instance;
	}
	
	
	private final String[] EN_DIGITS = {
        "zero", "one", "two", "three", "four",
        "five", "six", "seven", "eight", "nine"
    };
	
	
	// Tạm thời chỉ lấy 51 bậc đơn vị của EN -> có thể đọc được 51*3 (153 chữ số)
    private final String[] EN_UNITS = {
        "", "thousands", "millions", "billions", "trillions", "quadrillions",
        "quintillions", "sextillions", "septillions", "octillions", "nonillions",
        "decillions", "undecillions", "duodecillions", "tredecillions",
        "quattuordecillions", "quindecillions", "sexdecillions", "septendecillions",
        "octodecillions", "novemdecillions", "nigintillions", "unvigintillions",
        "duovigintillions", "tresvigintillions", "quattuorvigintillions",
        "quinvigintillions", "sexvigintillions", "septenvigintillions", "octovigintillions",
        "novemvigintillions", "trigintillions", "untrigintillions", "duotrigintillions",
        "trestrigintillions", "quattuortrigintillions", "quintrigintillions",
        "sextrigintillions", "septentrigintillions", "octotrigintillions",
        "novemtrigintillions", "quadragintillions", "unquadragintillions",
        "duoquadragintillions", "tresquadragintillions", "quattuorquadragintillions",
        "quinquadragintillions", "sexquadragintillions", "septenquadragintillions",
        "octoquadragintillions", "novemquadragintillions"
    };

    private final String[] secondDigitName = {
        "", "", "twenty", "thirty", "forty", "fifty",
        "sixty", "seventy", "eighty", "ninety"
    };

    @Override
    protected String[] getDigitLiterals() 
	{
		return EN_DIGITS;
	}
    
	@Override
	protected String getUnit(int index) 
	{
		return EN_UNITS[index];
	}

	// Đọc số có 2 chữ số bc ; b != 0 (EN)
	@Override
	protected String readTwoDigit(int b, int c) 
	{
		if (b == 1) {
            switch (c) {
                case 0:
                    return "ten";
                case 1:
                    return "eleven";
                case 2:
                    return "twelve";
                case 3:
                    return "thirteen";
                case 5:
                    return "fifteen";
                default:
                    return EN_DIGITS[c] + "teen";
            }
        } else {
            if (c == 0) {
                return secondDigitName[b];
            } else {
            	// c != 0, cần đọc thêm chữ số c
                return secondDigitName[b] + "-" + EN_DIGITS[c];
            }
        }
	}
	
	// Đọc số có 3 chữ số abc (a có thể bằng 0)
	@Override
	protected String readThreeDigit(int a, int b, int c) 
	{
		StringBuilder res = new StringBuilder();
		
		// TH a == 0 : không đọc chữ số a
	    // TH a != 0 : cần phải đọc chữ số a
        if (a != 0) {
            res.append(EN_DIGITS[a]).append(" hundred");
        }
        
        if (b == 0) {
        	// TH b == 0 : số có dạng a00 hoặc a0c (c != 0)
            // Ta chỉ đọc trường hợp a0c
            if (c != 0) {
                if (a != 0) res.append(" ");
                res.append("and ").append(EN_DIGITS[c]);
            }
        } else {
            res.append(" and ").append(readTwoDigit(b, c));
        }
        
        return res.toString();
	}
	
}
