package cn.mcwlc.wildrnesssurvival.em;

/**
 * @author DongShaoNB
 */

public enum Quality {

    COMMON("common", "普通", "§f"),
    RARE("rare", "稀有", "§9"),
    EPIC("epic", "史诗", "§5"),
    LEGEND("legend", "传奇", "§6");

    private final String qualityId;
    private final String qualityName;
    private final String qualityColor;

    Quality(String qualityId, String qualityName, String qualityColor) {
        this.qualityId = qualityId;
        this.qualityName = qualityName;
        this.qualityColor = qualityColor;
    }

    public String getQualityId() {
        return qualityId;
    }

    public String getQualityName() {
        return qualityName;
    }

    public String getQualityColor() {
        return qualityColor;
    }

    public static Quality getQuality(String qualityId) {
        for (Quality quality : Quality.values()) {
            if (quality.getQualityId().equalsIgnoreCase(qualityId)) {
                return quality;
            }
        }
        throw new IllegalArgumentException("未知的属性ID: " + qualityId);
    }

}
