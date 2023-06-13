package cn.mcbeserver.wildrnesssurvival.em;

/**
 * @author DongShaoNB
 */

public enum Attribute {

    HEALTH("health", "生命"),
    ATTACK("attack", "攻击"),
    DEFENSE("defense", "防御"),
    SPEED("speed", "速度");

    private final String attributeId;
    private final String attributeName;

    Attribute(String attributeId, String attributeName) {
        this.attributeId = attributeId;
        this.attributeName = attributeName;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public static Attribute getAttribute(String attributeId) {
        for (Attribute attribute : Attribute.values()) {
            if (attribute.getAttributeId().equalsIgnoreCase(attributeId)) {
                return attribute;
            }
        }
        throw new IllegalArgumentException("未知的属性ID: " + attributeId);
    }

}
