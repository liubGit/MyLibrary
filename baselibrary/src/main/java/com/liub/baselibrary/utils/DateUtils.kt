package com.liub.baselibrary.utils
import android.content.Context
import android.text.TextUtils
import android.widget.TextView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Create by liub on 2019/4/8
 * Describe:
 */
object DateUtils {

    //缓存时间
    var TIME_YEAR = 15552000 //60 * 60 * 24 *180 单位(秒)
    var TIME_HOUR = 3600 //60 * 60 * 1 单位(秒)
    var TIME_DAY = 86400 //60 * 60 * 24 单位(秒)   /1440
    /**
     * 指定日期格式 yyyyMMddHHmmss
     */
    val DATE_FORMAT_1 = "yyyyMMddHHmmss"

    /**
     * 指定日期格式 yyyy-MM-dd HH:mm:ss
     */
    val DATE_FORMAT_2 = "yyyy-MM-dd HH:mm:ss"

    /**
     * 指定日期格式 yyyy-MM-dd'T'HH:mm:ssZ
     */
    val DATE_FORMAT_3 = "yyyy-MM-dd'T'HH:mm:ssZ"

    /**
     * 指定日期格式 yyyy-MM-dd
     */
    val DATE_FORMAT_4 = "yyyy-MM-dd"

    /**
     * 指定日期格式 yyyy.M.d
     */
    val DATE_FORMAT_5 = "yyyy.M.d"

    /**
     * 指定日期格式yyyy-MM-dd HH:mm
     */
    val DATE_FORMAT_6 = "yyyy-MM-dd HH:mm"

    /**
     * 指定日期格式HH:mm
     */
    val DATE_FORMAT_7 = "HH:mm"

    /**
     * 指定日期格式MM-dd HH:mm
     */
    val DATE_FORMAT_8 = "MM-dd HH:mm"

    /**
     * 指定日期格式HH:MM:SS
     */
    val DATE_FORMAT_9 = "HH:MM:SS"

    /**
     * 指定日期格式HH:mm
     */
    val DATE_FORMAT_10 = "MM-dd"

    /**
     * 指定日期格式yy-MM-dd HH:mm
     */
    val DATE_FORMAT_11 = "yy-MM-dd HH:mm"

    /**
     * 日期排序类型-升序
     */
    val DATE_ORDER_ASC = 0

    /**
     * 日期排序类型-降序
     */
    val DATE_ORDER_DESC = 1

    /**
     * 根据指定格式，获取现在时间
     */
    fun getNowDateFormat(format: String): String {
        val currentTime = Date()
        val formatter = SimpleDateFormat(format)
        return formatter.format(currentTime)
    }

    /**
     * 把String日期转成制定格式
     */
    fun getDateFormat(getDateString: String, format: String): String {
        if (!TextUtils.isEmpty(getDateString)) {
            val simpleDateFormat = SimpleDateFormat(format)

            var getDate: Date? = null
            try {
                getDate = getFormat(DATE_FORMAT_6).parse(getDateString)
            } catch (e: ParseException) {
                e.printStackTrace()
                return getDateString
            }

            return simpleDateFormat.format(getDate)
        }
        return getDateString
    }


    /**
     * 根据时间戳转成指定的format格式
     *
     * @param timeMillis
     * @param format
     * @return
     */
    fun formatDate(timeMillis: String, format: String): String {
        var date: Date? = null
        if (!TextUtils.isEmpty(timeMillis)) {
            try {
                date = Date(java.lang.Long.parseLong(timeMillis))
            } catch (e: NumberFormatException) {
                date = Date()
            }

        } else {
            date = Date()
        }

        val formatter = SimpleDateFormat(format)
        return formatter.format(date)
    }

    fun formatDate(timeMillis: Long, format: String): String {
        var date: Date? = null
        if (timeMillis > 0) {
            date = Date(timeMillis)
        } else {
            date = Date()
        }

        val formatter = SimpleDateFormat(format)
        return formatter.format(date)
    }

    /**
     * format处理
     *
     * @param getDateString
     * @return
     */
    fun getDateFormatForLetter(getDateString: String,
                               context: Context): String {

        if (TextUtils.isEmpty(getDateString)) {
            return ""
        }

        val simpleDateFormat = SimpleDateFormat(
                "yyyy-MM-dd HH:mm")

        var getDate: Date? = null
        try {
            getDate = getFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(getDateString)
        } catch (e: ParseException) {
            getDate = Date()
        }

        return simpleDateFormat.format(getDate)
    }

    /**
     * 当前时间计算
     *
     * @param getDateString
     * @return
     */
    fun getTimeStringDisplay(getDateString: String): String {
        var getDate: Date? = null
        try {
            getDate = getFormat(DATE_FORMAT_6).parse(getDateString)
        } catch (e: ParseException) {
            e.printStackTrace()
            getDate = Date()
        }

        val getTime = getDate!!.time

        val currTime = System.currentTimeMillis()
        val formatSysDate = Date(currTime)

        if (getDate.date < formatSysDate.date) {
            val simpleDateFormat = SimpleDateFormat(DATE_FORMAT_10)
            return simpleDateFormat.format(getDate)
        } else {
            // 判断当前总天数
            val sysMonth = formatSysDate.month + 1
            val sysYear = formatSysDate.year

            // 计算服务器返回时间与当前时间差值
            val seconds = (currTime - getTime) / 1000
            val minute = seconds / 60
            val hours = minute / 60
            val day = hours / 24
            val month = day / calculationDaysOfMonth(sysYear, sysMonth)
            val year = month / 12

            if (year > 0 || month > 0 || day > 0) {
                val simpleDateFormat = SimpleDateFormat(
                        DATE_FORMAT_10)
                return simpleDateFormat.format(getDate)
            } else if (minute > 2) {
                val simpleDateFormat = SimpleDateFormat(
                        DATE_FORMAT_7)
                return simpleDateFormat.format(getDate)
            } else {
                return "" //都换成分钟前
            }
        }

    }

    @JvmOverloads
    fun getTimeDisplay(getDateString: String, timeZone: Boolean = true): String {
        var getDate: Date? = null
        try {
            if (getDateString.contains("T")) {
                if (timeZone) {
                    getDate = getFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(getDateString)
                } else {
                    getDate = getFormat("yyyyMMdd'T'HH:mm:ss").parse(getDateString)
                }
            } else {
                getDate = getFormat(DATE_FORMAT_6).parse(getDateString)
            }

        } catch (e: ParseException) {
            e.printStackTrace()
            getDate = Date()
        }

        val getTime = getDate!!.time

        val currTime = System.currentTimeMillis()
        val formatSysDate = Date(currTime)

        // 判断当前总天数
        val sysMonth = formatSysDate.month + 1
        val sysYear = formatSysDate.year

        // 计算服务器返回时间与当前时间差值
        val seconds = (currTime - getTime) / 1000
        val minute = seconds / 60
        val hours = minute / 60
        val day = hours / 24
        val month = day / calculationDaysOfMonth(sysYear, sysMonth)
        val year = month / 12

        if (year > 0 || month > 0 || day > 0) {
            val simpleDateFormat = SimpleDateFormat(
                    "yyyy-MM-dd HH:mm")
            return simpleDateFormat.format(getDate)
        } else return if (hours > 0) {
            hours.toString() + ""
        } else if (minute > 0) {
            minute.toString() + ""
        } else if (seconds > 0) {
            seconds.toString() + ""
        } else {
            ""
        }
    }


    fun getFormat(partten: String): SimpleDateFormat {
        return SimpleDateFormat(partten)
    }

    fun getFormatDataString(date: Date): String {
        return SimpleDateFormat("yyyy-MM-dd").format(date)
    }

    fun parseJsonDate(date: String): String {
        return date.substring(0, 10)
    }

    /**
     * 计算月数
     *
     * @return
     */
    private fun calculationDaysOfMonth(year: Int, month: Int): Int {
        var day = 0
        when (month) {
            // 31天
            1, 3, 5, 7, 8, 10, 12 -> day = 31
            // 30天
            4, 6, 9, 11 -> day = 30
            // 计算2月天数
            2 -> day = if (year % 100 == 0)
                if (year % 400 == 0) 29 else 28
            else if (year % 4 == 0) 29 else 28
        }

        return day
    }

    /**
     * 日期排序
     *
     * @param dates     日期列表
     * @param orderType 排序类型：DATE_ORDER_ASC，DATE_ORDER_DESC
     * @return 排序后的list
     *
     *
     * 用法 ArrayList<Date> dates = new ArrayList<Date>(); String dateStr
     * = "2011-10-25T00:00:00+08:00"; Date getDate =
     * getFormat("yyyy-MM-dd").parse(dateStr); dates.add(getDate);
    </Date></Date> *
     *
     * orderDate(dates, DATE_ORDER_ASC);
     */
    fun orderDate(dates: ArrayList<Date>, orderType: Int): ArrayList<Date> {
        val comp = DateComparator(orderType)
        Collections.sort(dates, comp)
        return dates
    }

    private class DateComparator(internal var orderType: Int) : Comparator<Date> {

        override fun compare(d1: Date, d2: Date): Int {
            return if (d1.time > d2.time) {
                if (orderType == DATE_ORDER_ASC) {
                    1
                } else {
                    -1
                }
            } else {
                if (d1.time == d2.time) {
                    0
                } else {
                    if (orderType == DATE_ORDER_DESC) {
                        1
                    } else {
                        -1
                    }
                }
            }
        }
    }

    /***
     * 返回日期 ，如果参数为0返回今天。
     */
    fun getNowDate(afterDay: Int): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, afterDay)
        return dateFormat.format(calendar.time)
    }

    fun getNowCalendar(afterDay: Int): Calendar {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, afterDay)
        return calendar
    }

    /**
     * 计算天数方法
     *
     * @param start
     * @param end
     * @return
     */
    fun getDaysBetween(start: String?, end: String?): Int {
        var days = 0

        var isNegative = false
        if (!TextUtils.isEmpty(start) && !TextUtils.isEmpty(end)) {
            try {
                val format = SimpleDateFormat("yyyy-MM-dd")
                val startDate = format.parse(start)

                var startTime = Calendar.getInstance()
                startTime.clear()
                startTime.time = startDate

                val endDate = format.parse(end)
                var endTime = Calendar.getInstance()
                endTime.clear()
                endTime.time = endDate

                if (startTime.after(endTime)) {
                    val swap = startTime
                    startTime = endTime
                    endTime = swap
                    isNegative = true

                }
                days = endTime.get(Calendar.DAY_OF_YEAR) - startTime.get(Calendar.DAY_OF_YEAR)
                val y2 = endTime.get(Calendar.YEAR)
                if (startTime.get(Calendar.YEAR) != y2) {
                    startTime = startTime.clone() as Calendar
                    do {
                        days += startTime
                                .getActualMaximum(Calendar.DAY_OF_YEAR)// 得到当年的实际天数
                        startTime.add(Calendar.YEAR, 1)
                    } while (startTime.get(Calendar.YEAR) != y2)
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }

        }


        return if (isNegative && days != 0) {
            -days
        } else {
            days
        }
    }

    /**
     * 计算两个时间相隔分钟
     *
     * @param startTime
     * @param endTime
     * @return
     */
    fun getIntervalMinutes(startTime: String, endTime: String): Long {
        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
            try {
                val format = SimpleDateFormat("yyyy-MM-dd HH:mm")

                val startDate = format.parse(startTime)
                val start = Calendar.getInstance()
                start.clear()
                start.time = startDate

                val endDate = format.parse(endTime)
                val end = Calendar.getInstance()
                end.clear()
                end.time = endDate

                // 把时间转成毫秒
                val time1 = start.timeInMillis
                val time2 = end.timeInMillis

                // 计算两个时间相差多少毫秒
                val diff = time1 - time2

                // 把相差的毫秒转成分钟
                val diffMin = diff / (60 * 1000)

                run {
                    /*
                // 相差小时
                long diffHours = diff / (60 * 60 * 1000);
                System.out.println("Difference in hours " + diffHours);

                // 相差天
                long diffDays = diff / (24 * 60 * 60 * 1000);
            */
                }

                return diffMin
            } catch (e: ParseException) {
                e.printStackTrace()
            }

        }
        return 0
    }

    /**
     * 把yyyy-MM-dd'T'HH:mm:ssZ类型日期转成yyyy.MM.dd类型
     *
     * @param str
     * @return
     */
    fun parseStrToDate(str: String?): String? {
        if (str != null && str.length > 0) {
            var date: Date? = null
            try {
                date = getFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(str)
            } catch (ex: Exception) {
            }

            val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
            return simpleDateFormat.format(date)
        } else {
            return null
        }
    }

    /**
     * 根据日期获取星期
     *
     * @param str
     * @return
     */
    fun getWeekday(str: String, context: Context): String {
        try {
            val date = getFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(str)
            if (date != null) {
                return weekdayFormat(date.day, context) // 0..6 0:sunday,
            }
        } catch (e: ParseException) {
        }

        return ""
    }

    private fun weekdayFormat(day: Int, context: Context): String {
        when (day) {
            0 -> return "周日"
            1 -> return "周一"
            2 -> return "周二"
            3 -> return "周三"
            4 -> return "周四"
            5 -> return "周五"
            6 -> return "周六"
            else -> return ""
        }
    }

    /**
     * 获取日期显示
     *
     * @param endTime
     * @return
     */
    fun getDateIncludeWeek(endTime: Calendar): String {
        val afterDay = getDaysBetween(endTime)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        // Calendar calendar = Calendar.getInstance();
        // calendar.add(Calendar.DAY_OF_MONTH, afterDay);
        when (afterDay) {
            -1 -> return dateFormat.format(endTime.time)
            0 -> return dateFormat.format(endTime.time) + " 今天"
            1 -> return dateFormat.format(endTime.time) + " 明天"
            2 -> return dateFormat.format(endTime.time) + " 后天"
        }
        return dateFormat.format(endTime.time) + " " + getWeekDay(endTime)
    }

    /**
     * 去掉星期只截取日期
     *
     * @param temp
     * @return
     */
    fun getDateNoWeek(temp: String?): String? {
        return if (temp != null && temp.length > 10) {
            temp.substring(0, 10)
        } else temp
    }

    fun getDateNoWeek(tv: TextView?): String? {
        if (tv != null) {
            val temp = tv.text.toString()
            return getDateNoWeek(temp)
        }
        return ""
    }

    /**
     * 把2012-02-20 星期一 转化Date 对象
     *
     * @param temp
     * @return
     */
    fun String2Date(temp: String): Date? {
        val str = getDateNoWeek(temp)
        if (str != null && str.length == 10) {
            val format = SimpleDateFormat("yyyy-MM-dd")

            try {
                return format.parse(str)
            } catch (e: ParseException) {
            }

        }
        return null
    }

    /**
     * 返回指定格式的date对象
     *
     * @param strDate
     * @param strFormat
     * @return
     */
    fun getString2FormatDate(strDate: String, strFormat: String): Date? {
        if (!TextUtils.isEmpty(strDate)) {
            try {
                val format = SimpleDateFormat(strFormat)
                return format.parse(strDate)
            } catch (e: ParseException) {
            }

        }
        return null
    }

    /**
     * 从当天开始计算天数
     *
     * @return
     */
    fun getDaysBetween(endTime: Calendar): Int {
        var endTimeU=endTime
        var days = 0
        var isNegative = false
        var startTime = Calendar.getInstance()
        try {
            if (startTime.after(endTimeU)) {
                val swap = startTime
                startTime = endTimeU
                endTimeU = swap
                isNegative = true
            }
            days = endTimeU.get(Calendar.DAY_OF_YEAR) - startTime.get(Calendar.DAY_OF_YEAR)
            val y2 = endTimeU.get(Calendar.YEAR)
            if (startTime.get(Calendar.YEAR) != y2) {
                startTime = startTime.clone() as Calendar
                do {
                    days += startTime.getActualMaximum(Calendar.DAY_OF_YEAR)// 得到当年的实际天数
                    startTime.add(Calendar.YEAR, 1)
                } while (startTime.get(Calendar.YEAR) != y2)
            }
        } catch (e: Exception) {
        }

        return if (isNegative && days != 0) {
            -days
        } else {
            days
        }
    }

    /**
     * 获取日期星期
     *
     * @param c
     * @return
     */
    private fun getWeekDay(c: Calendar?): String {
        if (c == null) {
            return "周一"
        }
        if (Calendar.MONDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "周一"
        }
        if (Calendar.TUESDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "周二"
        }
        if (Calendar.WEDNESDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "周三"
        }
        if (Calendar.THURSDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "周四"
        }
        if (Calendar.FRIDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "周五"
        }
        if (Calendar.SATURDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "周六"
        }
        return if (Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK)) {
            "周日"
        } else "星期一"
    }

    /**
     * 用于计算两个"2012-02-12  星期一"格式 日期之间间隔天数
     *
     * @param start
     * @param end
     * @return
     * @author Administrator
     */
    fun getDaysBetweenForContainWeek(start: String, end: String): String {
        return getDaysBetween(getDateNoWeek(start), getDateNoWeek(end)).toString() + "天"
    }

    /**
     * 用于计算两个"2012-02-12  星期一"格式 日期之间间隔天数
     *
     * @param start
     * @param end
     * @return
     * @author Administrator
     */
    fun getDaysBetweenForContainWeek(start: TextView,
                                     end: TextView): String {
        return getDaysBetween(getDateNoWeek(start.text.toString()),
                getDateNoWeek(end.text.toString())).toString() + "天"
    }

    /**
     * 将TextView里字符串转换成Calendar
     */
    fun viewText2Calendar(tv: TextView?): Calendar {
        val date = tv?.text?.toString() ?: ""
        return string2Calendar(date)
    }

    /**
     * 将字符串转换成Calendar
     *
     * @param date
     * @return
     */
    fun string2Calendar(date: String?): Calendar {
        var date = date
        date = getDateNoWeek(date)
        val format = SimpleDateFormat("yyyy-MM-dd")
        var d: Date
        try {
            d = format.parse(date)
        } catch (e: ParseException) {
            d = Date()
        }

        val mCalendar = Calendar.getInstance().clone() as Calendar
        mCalendar.time = d
        return mCalendar
    }

    /**
     * 通过2012-03-07 获取 2012-03-07 星期三
     *
     * @param endTime
     * @return
     */
    fun getDateIncludeWeek(endTime: String): String {
        val endTimeCalendar = string2Calendar(endTime)
        return getDateIncludeWeek(endTimeCalendar)
    }

    /**
     * 时间大小比较
     *
     * @param oldDate
     * @param defDate
     * @return oldDate小于defDate
     */
    fun compareToDate(oldDate: String, defDate: String): Boolean {
        if (TextUtils.isEmpty(oldDate) || TextUtils.isEmpty(defDate)) {
            return true
        }

        val c1 = Calendar.getInstance()
        val c2 = Calendar.getInstance()

        try {
            val df = SimpleDateFormat(DATE_FORMAT_8)
            c1.time = df.parse(oldDate)
            c2.time = df.parse(defDate)
        } catch (e: Exception) {
        }

        val result = c1.compareTo(c2)
        return if (result < 0) {
            true
        } else false
// if (result == 0) {
        // DXLogUtil.e(“c1相等c2”);
        // } else if(result<0) {
        // DXLogUtil.e(“c1小于c2”);
        // } else {
        // DXLogUtil.e(“c1大于c2”);
        // }
    }

    /**
     * 判断指定日期是否为今天
     *
     * @param txtDate 格式必须为DATE_FORMAT_6
     * @return
     */
    fun isToday(txtDate: String): Boolean {
        if (TextUtils.isEmpty(txtDate)) {
            return false
        }


        try {
            val format = SimpleDateFormat(DATE_FORMAT_6)
            val workDay = format.parse(txtDate)

            return isToday(workDay)

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return false
    }

    /**
     * 判断指定日期是否为今天
     *
     * @param workDay 指定的日期
     * @return
     */
    fun isToday(workDay: Date): Boolean {
        val c1 = Calendar.getInstance()
        val c2 = Calendar.getInstance()

        val currTime = Date()

        c1.time = workDay
        c2.time = currTime

        return if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)) {
            true
        } else {
            false
        }
    }

    /**
     * 判断指定时间和当前时间是否小于minute分钟
     *
     * @param txtDate 指定的时间
     * @return
     */
    fun isTimeMatch(txtDate: String, minute: Int): Boolean {
        var minute = minute
        if (TextUtils.isEmpty(txtDate)) {
            return false
        }

        if (minute == 0) {
            minute = 3
        }

        try {
            val format = SimpleDateFormat(DATE_FORMAT_2)
            val workDay = format.parse(txtDate)

            val c1 = Calendar.getInstance()
            val c2 = Calendar.getInstance()

            val currTime = Date()

            c1.time = workDay
            c2.time = currTime

            if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                    && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)
                    && c1.get(Calendar.HOUR_OF_DAY) == c2.get(Calendar.HOUR_OF_DAY)) {
                if (Math.abs(c1.get(Calendar.MINUTE) - c2.get(Calendar.MINUTE)) < minute) {
                    return true
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return false
    }

    /**
     * 判断昨天，今天
     *
     * @param date
     * @return 昨天=今天0点前24小时至今天0点，
     */
    fun formatMyTweetDetailTime(date: String): String? {

        var day = 0

        val sdf = SimpleDateFormat("yyyy-MM-dd")

        try {

            val d1 = Date()//当前时间

            val d2 = sdf.parse(date)//传进的时间
            //
            sdf.format(d2)

            val cha = d2.time - d1.time

            day = (cha / (1000 * 60 * 60 * 24)).toInt()

            if (day == 0) {
                return "今天"
            } else if (day == -1) {
                return "昨天"
            } else {
                if (sdf != null) {

                    val mCalendar = Calendar.getInstance()
                    mCalendar.timeInMillis = d2.time
                    val month = mCalendar.get(Calendar.MONTH) + 1
                    val dayTime = mCalendar.get(Calendar.DAY_OF_MONTH)
                    return if (dayTime < 10) {
                        month.toString() + "月0" + dayTime + "日"//formatTweetDetailTime(date, false);
                    } else {
                        month.toString() + "月" + dayTime + "日"
                    }
                }
                // return d2.getMonth()+”月”+d2.getDay()+”日”;//formatTweetDetailTime(date, false);
            }

        } catch (e: ParseException) {

            // TODO Auto-generated catch block

            e.printStackTrace()

        }

        return null

    }


    /**
     * 比较两个日期是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    fun isSameDayCompareDate(date1: String, date2: String): Boolean {
        if (TextUtils.isEmpty(date1) || TextUtils.isEmpty(date2)) {
            return false
        }
        val c1 = Calendar.getInstance()
        val c2 = Calendar.getInstance()

        try {
            val df = SimpleDateFormat(DATE_FORMAT_4)
            c1.time = df.parse(date1)
            c2.time = df.parse(date2)
        } catch (e: Exception) {
        }

        return if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)) {
            true
        } else {
            false
        }

    }

    /**
     * 判断指定日期是否为今天
     *
     * @param txtDate 格式必须为DATE_FORMAT_6
     * @return
     */
    fun isSameYearCompareDate(txtDate: String): Boolean {
        if (TextUtils.isEmpty(txtDate)) {
            return false
        }


        try {
            val format = SimpleDateFormat(DATE_FORMAT_6)
            val workDay = format.parse(txtDate)

            val c1 = Calendar.getInstance()
            val c2 = Calendar.getInstance()

            val currTime = Date()

            c1.time = workDay
            c2.time = currTime

            return if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) {
                true
            } else {
                false
            }

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return false
    }


    /**
     * 比较两个日期是否是同一年
     *
     * @param date1
     * @param date2
     * @return
     */
    fun isSameYearCompareDate(date1: String, date2: String): Boolean {
        if (TextUtils.isEmpty(date1) || TextUtils.isEmpty(date2)) {
            return false
        }

        val c1 = Calendar.getInstance()
        val c2 = Calendar.getInstance()

        try {
            val df = SimpleDateFormat(DATE_FORMAT_4)
            c1.time = df.parse(date1)
            c2.time = df.parse(date2)
        } catch (e: Exception) {
        }

        return if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) {
            true
        } else {
            false
        }

    }
}
