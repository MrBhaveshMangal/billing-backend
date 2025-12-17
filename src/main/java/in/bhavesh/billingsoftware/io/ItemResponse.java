package in.bhavesh.billingsoftware.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResponse {

    private String itemid;
    private String name;
    private BigDecimal price;
    private String categoryid;
    private String description;
    private String categoryName;
    private String imgurl;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
