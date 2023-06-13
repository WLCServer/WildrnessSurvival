package cn.mcbeserver.wildrnesssurvival.em;

/**
 * @author DongShaoNB
 */

public enum Skill {
    COLLECT("collect", "采集"),
    MAKE("make", "制作"),
    FIGHT("fight", "战斗");

    private final String skillId;
    private final String skillName;

    Skill(String skillId, String skillName) {
        this.skillId = skillId;
        this.skillName = skillName;
    }

    public String getSkillId() {
        return skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public static Skill getSkill(String skillId) {
        for (Skill skill : Skill.values()) {
            if (skill.getSkillId().equalsIgnoreCase(skillId)) {
                return skill;
            }
        }
        throw new IllegalArgumentException("未知的技能ID: " + skillId);
    }
}