package hlf.kafkaerrorhandling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WidgetEvent {

    private static Random random = new Random();

    public static String[] WIDGET_TYPES = {
            "Strong",
            "Weak",
            "Tall",
            "Short",
            "Blue",
            "Green",
            "Pink",
            "Orange",
            "Lovely",
            "Ugly",
            "Intelligent",
            "Silly",
            "Incredible",
    };

    public static String[] WIDGET_EVENT_TYPES = {
            "Created",
            "Updated",
            "Archived",
            "Shipped",
            "Deleted"
    };

    private String id;
    private String widgetType;
    private String eventType;

    public static WidgetEvent randomWidgetEvent() {
        var random = new Random();

        return new WidgetEvent(
                String.valueOf(Math.abs(random.nextLong())),
                randomOf(WIDGET_TYPES),
                randomOf(WIDGET_EVENT_TYPES) );
    }

    public static WidgetEvent randomWidgetEvent(int id) {
        var random = new Random();

        return new WidgetEvent(
                String.valueOf(id),
                randomOf(WIDGET_TYPES),
                randomOf(WIDGET_EVENT_TYPES) );
    }

    public static String randomOf(String[] things) {
        var index = Math.abs(random.nextInt() % things.length);
        return things[index];
    }
}
