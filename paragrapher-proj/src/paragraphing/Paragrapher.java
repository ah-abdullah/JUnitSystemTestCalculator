/**
 * 
 */
package paragraphing;

import java.util.ArrayList;

/**
 * @author XYZ
 *
 */
public class Paragrapher implements ParagrapherI {
	DestinationI destination ;
	int width = 20;
	private ArrayList<String> lines = new ArrayList<String>() ;
	String str = "";

	public Paragrapher(DestinationI dest) {
		destination = dest ;
	}

	/** 
	 * @see paragraphing.ParagrapherI#setWidth(int)
	 */
	@Override
	public void setWidth(int width) {
		// TODO Auto-generated method stub
		this.width = width;
	}

	/**
	 * @see paragraphing.ParagrapherI#addWord(java.lang.String[])
	 */
	@Override
	public void addWord(String[] parts) {
		// TODO Auto-generated method stub
		if (lines.isEmpty()) {
			lines.add("<p>");
		}
		
		if (str.isEmpty()) {
			if (parts[0].length() <= this.width) {
				str = parts[0];
			}
			else {
				String subString1 = parts[0].substring(0, this.width);
				String subString2 = parts[0].substring(this.width);
				str = subString1;
				lines.add("<line>" + str + "-" + "</line>");
				str = subString2;
			}
			
			for (int i = 1; i < parts.length; ++i)
			{
				if ((str + parts[i]).length() < this.width) {
					str = str + parts[i];
				} else {
					lines.add("<line>" + str + "-" + "</line>");
					str = parts[i];
				}
			}
		} else {			
			for (int i = 0; i < parts.length; ++i)
			{
				if (i == 0) {
					if ((str + " " + parts[i]).length() <= this.width) {
						str = str + " " + parts[i];
					}
					else {
						if ((str + " ").length() > this.width) {
							lines.add("<line>" + str + "</line>");
							str = parts[i];
						} else
						{
							int allowedLength = this.width - (str + " ").length();
							String subString1 = parts[i].substring(0, allowedLength);
							String subString2 = parts[i].substring(allowedLength);
							if (subString1.isEmpty()) {
								lines.add("<line>" + str + "-" + "</line>");
								str = subString2;
							} else {
								str = str +  subString1;
								lines.add("<line>" + str + "-" + "</line>");
								str = subString2;
							}
						}
					}
				} else {
					if ((str + parts[i]).length() < this.width) {
						str = str + parts[i];
					} else {
						lines.add("<line>" + str + "-" + "</line>");
						str = parts[i];
					}
				}
			}
//			lines.add("<line>" + str + "</line>");
		}
//		lines.add("<line>" + str + "</line>");

	}

	/**
	 * @see paragraphing.ParagrapherI#addWord(java.lang.String)
	 */
	@Override
	public void addWord(String word) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see paragraphing.ParagrapherI#ship()
	 */
	@Override
	public void ship() {
		// TODO Auto-generated method stub
		if (!str.isEmpty()) {
			lines.add("<line>" + str + "</line>");
			str = "";
		}
		if (!lines.isEmpty()) {
			lines.add("</p>");
			String[] linesString = new String[ lines.size() ] ;
			int i = 0 ;
			for( String line : lines ) linesString[i++] = line ;
			destination.addLines(linesString);
		}
	}

}
