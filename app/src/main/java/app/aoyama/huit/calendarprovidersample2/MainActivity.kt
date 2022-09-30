package app.aoyama.huit.calendarprovidersample2

import android.R.attr.accountType
import android.R.attr.visible
import android.content.ContentValues.TAG
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.text.format.DateUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import app.aoyama.huit.calendarprovidersample2.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val EVENT_PROJECTION: Array<String> = arrayOf(
        CalendarContract.Events.CALENDAR_ID,
        CalendarContract.Events.TITLE,
        CalendarContract.Events.DTSTART,
        CalendarContract.Events.DTEND,
        CalendarContract.Events.ALL_DAY,
        CalendarContract.Events.ORGANIZER,
    )

    private val PROJECTION_ID_INDEX: Int = 0
    private val PROJECTION_CALENDAR_ID_INDEX: Int = 1
    private val PROJECTION_TITLE_INDEX: Int = 2
    private val PROJECTION_DTSTART_INDEX: Int = 3
    private val PROJECTION_DTEND_INDEX: Int = 4
    private val PROJECTION_ALL_DAY_INDEX: Int = 5
    private val PROJECTION_ORGANIZER_INDEX: Int = 6

    private fun getDateTimeText(
        context: Context,
        timeZone: String,
        dateTimeInMillis: Long
    ): String? {
        val calendar: Calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone(timeZone))
        calendar.setTimeInMillis(dateTimeInMillis)
        return DateUtils.formatDateRange(
            context,
            Formatter(),
            calendar.getTimeInMillis(),
            calendar.getTimeInMillis(),
            DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR,
            timeZone
        ).toString()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // クエリ条件を設定する
        val uri: Uri = CalendarContract.Events.CONTENT_URI
//        val selection = "(" + CalendarContract.Events.CALENDAR_ID + " = ?)"
        val selection: String = "((${CalendarContract.Events.CALENDAR_ID} = ?) AND (" +
                "${CalendarContract.Events.TITLE} = ?) AND (" +
                "${CalendarContract.Events.DTSTART} = ?) AND (" +
                "${CalendarContract.Events.DTEND} = ?) AND (" +
                "${CalendarContract.Events.ALL_DAY} = ?) AND (" +
                "${CalendarContract.Events.ORGANIZER} = ?))"
//        val selectionArgs = arrayOf(targetCalendarId.toString())
        val selectionArgs: Array<String> =
            arrayOf("eitoao88@gmail.com", "com.google", "eitoao88@gmail.com")


        // クエリを発行してカーソルを取得する
        val cr = contentResolver
        val cur: Cursor? = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null)

        if (cur != null) {
            while (cur.moveToNext()) {
                // カーソルから各プロパティを取得する
                val id: Long = cur.getLong(PROJECTION_ID_INDEX)
                val calendarId: Long = cur.getLong(PROJECTION_CALENDAR_ID_INDEX)
                val title: String = cur.getString(PROJECTION_TITLE_INDEX)
                val dtStart: Long = cur.getLong(PROJECTION_DTSTART_INDEX)
                val dtEnd: Long = cur.getLong(PROJECTION_DTEND_INDEX)
                val allDay: Int = cur.getInt(PROJECTION_ALL_DAY_INDEX)
                val organizer: String = cur.getString(PROJECTION_ORGANIZER_INDEX)

                // ログ出力
                binding.button.setOnClickListener {
                    println(id)
                    println(calendarId)
                    println(title)
                    println(dtStart)
                    println(dtEnd)
                    println(allDay)
                    println(organizer)
                    println("アイウエオ")

                }
            }
        }

    }
}