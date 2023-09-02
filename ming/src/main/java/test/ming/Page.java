package test.ming;
import lombok.*;

@Getter
@AllArgsConstructor
public class Page {
    private final String id;

    private final String title;

    private final String content;

    private String parentId;

}
