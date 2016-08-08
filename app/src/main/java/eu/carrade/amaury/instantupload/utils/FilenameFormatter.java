package eu.carrade.amaury.instantupload.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Filename formatter with strftime-like tokens support.
 */
public class FilenameFormatter
{
    public static String formatFilename(String pattern, String originalFilename)
    {
        String internalFilename;
        String extension = "";

        if (StringUtils.contains(originalFilename, '.'))
        {
            String[] filenameParts = originalFilename.split("\\.");

            extension = "." + filenameParts[filenameParts.length - 1];
            internalFilename = StringUtils.join(Arrays.copyOfRange(filenameParts, 0, filenameParts.length - 1), ".");
        }
        else
        {
            internalFilename = originalFilename;
        }

        return new FilenameFormatter(pattern, internalFilename).format() + extension;
    }


    private final static char TOKEN_CHAR = '%';
    private final static char NO_INTERPRETATION_CHAR = '\'';


    private final String pattern;
    private final String originalName;

    public FilenameFormatter(String pattern, String originalName)
    {
        this.pattern = pattern;
        this.originalName = originalName;
    }

    public String format()
    {
        return format(new Date());
    }


    public String format(Date date)
    {
        if (!StringUtils.contains(pattern, '%'))
            return pattern;

        final StringBuilder builder = new StringBuilder();

        final Locale locale = Locale.getDefault();
        final Calendar calendar = Calendar.getInstance(locale);

        calendar.setTime(date);

        boolean disabledInterpretation = false;

        for (int i = 0; i < pattern.length(); i++)
        {
            char c = pattern.charAt(i);

            if (c == TOKEN_CHAR && !disabledInterpretation && i < pattern.length() - 1)
            {
                c = pattern.charAt(++i);

                switch (c)
                {
                    // Day of the week
                    case 'a':
                        builder.append(StringUtils.capitalize(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, locale).replace(".", "")));
                        break;

                    case 'A':
                        builder.append(StringUtils.capitalize(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale)));
                        break;

                    case 'w':
                        builder.append(calendar.get(Calendar.DAY_OF_WEEK) - 1);
                        break;

                    case 'd':
                        builder.append(StringUtils.leftPad(String.valueOf(calendar.get(Calendar.DATE)), 2, '0'));
                        break;

                    case 'b':
                        builder.append(StringUtils.capitalize(calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, locale).replace(".", "")));
                        break;

                    case 'B':
                        builder.append(StringUtils.capitalize(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale)));
                        break;

                    case 'm':
                        builder.append(StringUtils.leftPad(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2, '0'));
                        break;

                    case 'y':
                        builder.append(String.valueOf(calendar.get(Calendar.YEAR)).substring(2, 4));
                        break;

                    case 'Y':
                        builder.append(calendar.get(Calendar.YEAR));
                        break;

                    case 'H':
                        builder.append(StringUtils.leftPad(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)), 2, '0'));
                        break;

                    case 'I':
                        builder.append(StringUtils.leftPad(String.valueOf(calendar.get(Calendar.HOUR)), 2, '0'));
                        break;

                    case 'p':
                        builder.append(calendar.getDisplayName(Calendar.AM_PM, Calendar.SHORT, locale));
                        break;

                    case 'M':
                        builder.append(StringUtils.leftPad(String.valueOf(calendar.get(Calendar.MINUTE)), 2, '0'));
                        break;

                    case 'S':
                        builder.append(StringUtils.leftPad(String.valueOf(calendar.get(Calendar.SECOND)), 2, '0'));
                        break;

                    case 'f':
                        builder.append(StringUtils.leftPad(String.valueOf(calendar.get(Calendar.MILLISECOND)), 3, '0'));
                        break;

                    case 'z':
                        builder.append(calendar.getTimeZone().getDisplayName(true, TimeZone.SHORT));
                        break;

                    case 'Z':
                        builder.append(calendar.getTimeZone().getDisplayName(true, TimeZone.LONG));
                        break;

                    case 'j':
                        builder.append(StringUtils.leftPad(String.valueOf(calendar.get(Calendar.DAY_OF_YEAR)), 3, '0'));
                        break;

                    case 'U':
                    case 'W':
                        builder.append(StringUtils.leftPad(String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR)), 2, '0'));
                        break;

                    case 's':
                        builder.append(calendar.getTimeInMillis() / 1000);
                        break;

                    // Custom: random
                    case 'r':
                        builder.append(RandomStringUtils.random(6, true, true));
                        break;

                    // Custom: original name
                    case 'o':
                        builder.append(originalName);
                        break;

                    // Custom: original name without spaces
                    case 'O':
                        builder.append(originalName.replace(' ', '-'));
                        break;

                    case '%':
                        builder.append('%');
                        break;

                    // By default the token is ignored and inserted as-is
                    default:
                        builder.append('%').append(c);
                }
            }
            else if (c == NO_INTERPRETATION_CHAR)
            {
                if (pattern.length() > i + 1 && pattern.charAt(i + 1) == NO_INTERPRETATION_CHAR)
                {
                    builder.append(NO_INTERPRETATION_CHAR);
                    i++; // avoids interpretation of the other NO_INTERPRETATION_CHAR, causing inverted non-interpretation state
                }
                else disabledInterpretation = !disabledInterpretation;
            }
            else
            {
                builder.append(c);
            }
        }

        return builder.toString();
    }
}
