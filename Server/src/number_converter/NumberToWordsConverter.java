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
        int unitIndex = (len - 1) / 3;

		if (len % 3 != 0) {
			// Đọc riêng cụm đầu tiên có ít hơn 3 chữ số
			if (len % 3 == 2) {
				// Đọc số có hai chữ số
				res.append(readTwoDigit(digitArr[0], digitArr[1]));
			} else {
				// Đọc số có một chữ số
				res.append(digits[digitArr[0]]);
			}
			// Gắn cụm với đơn vị
			res.append(" " + getUnit(unitIndex) + ", ");
			--unitIndex;
		}

		int startIndex = len % 3; // vị trí bắt đầu của các cụm có đủ 3 chữ số
        for (int i = startIndex; i < len; i += 3) {
			int hundredsDigit = digitArr[i];
			int tensDigit = digitArr[i + 1];
			int onesDigit = digitArr[i + 2];

			if (hundredsDigit + tensDigit + onesDigit != 0) {
				// Đọc số có 3 chữ số và gắn với 
				res.append(readThreeDigit(hundredsDigit, tensDigit, onesDigit));
				// Gắn cụm với đơn vị
				res.append(" " + getUnit(unitIndex) + ", ");
			}

            --unitIndex;
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
