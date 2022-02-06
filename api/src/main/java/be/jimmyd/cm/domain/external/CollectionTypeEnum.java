package be.jimmyd.cm.domain.external;

public enum CollectionTypeEnum {
    MOVIES("Movies"),
    GAMES("Games"),
    MAGAZINES("Magazines"),
    BOOKS("Books"),
    DISKS("Disks"),
    COMICS("Comics");

    private final String type;

    CollectionTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
