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

    public static Skill getSkill(String skill) {
        for (Skill skill1 : Skill.values()) {
            if (skill1.getSkillId().equalsIgnoreCase(skill)) {
                return skill1;
            }
        }
        throw new IllegalArgumentException("未知的技能Id: " + skill);
    }
}