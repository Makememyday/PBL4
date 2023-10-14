package number_converter;

public abstract class NumberToWordsConverter
{
	abstract protected String[] getDigitLiterals();
	
	abstract protected String getUnit(int index);

	abstract protected String readTwoDigit(int b, int c);

	abstract protected String readThreeDigit(int a, int b, int c);
	

	protected void convertStringToArrayOfDigits(String s, int[] digitArr) 
	{
		for (int i = 0; i < s.length(); ++i) {
			digitArr[i] = s.charAt(i) - '0';
		}
	}

	protected String standardizeStringNumber(String s)
	{
		// Loại bỏ những chữ số 0 thừa ở đầu
		int indexOfLastZeroDigit = -1;
		while (s.charAt(indexOfLastZeroDigit + 1) == '0') {
			++indexOfLastZeroDigit;
		}

		return s.substring(indexOfLastZeroDigit + 1);
	}

	protected String convert(String s)
	{
		if (!isValidStringNumber(s)) {
			throw new IllegalArgumentException("String argument is invalid");
		}

		// Chuẩn hóa xâu s
		s = standardizeStringNumber(s);

		int[] digitArr = new int[s.length()];
		convertStringToArrayOfDigits(s, digitArr);
        int len = digitArr.length;
        
        String[] digits = getDigitLiterals();
        
        StringBuilder res = new StringBuilder();
        
        int unitIndex = 0;

        for (int i = len - 1; i >= 0;) {
        	if (i >= 2) {
                int c = digitArr[i];
                int b = digitArr[i - 1];
                int a = digitArr[i - 2];
                if (a + b + c != 0) {
                    res.insert(0, readThreeDigit(a, b, c) + " " + getUnit(unitIndex) + ", ");
                }
                i -= 3;
            } else {
                if (i == 1) {
                    res.insert(0, readTwoDigit(digitArr[0], digitArr[1]) + " " + getUnit(unitIndex) + ", ");
                } else {
                    res.insert(0, digits[digitArr[0]] + " " + getUnit(unitIndex) + ", ");
                }
                break;
            }
        	
            ++unitIndex;
        }

        // Remove the trailing ", "
        if (res.length() >= 2) {
            res.setLength(res.length() - 2);
        }

        return res.toString();
	}

	public static boolean isValidStringNumber(String s)
	{
		for (int i = 0; i < s.length(); ++i) {
			char ch = s.charAt(i);
			if (ch < '0' || ch > '9') return false;
		}
		return true;
	}
	
	public static String convert(String s, Language lan)
	{
		String res = "";
		
		switch (lan) 
		{
			case VI:
				res = VietnameseNumbertoWordsConverter.getInstance().convert(s);
				break;
			case EN:
				res = EnglishNumberToWordsConverter.getInstance().convert(s);
				break;
			//...
		}
		
		return res;
	}

}
