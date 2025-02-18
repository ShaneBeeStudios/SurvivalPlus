package com.shanebeestudios.survival.data;

import com.google.common.base.Preconditions;
import fr.mrmicky.fastboard.adventure.FastBoard;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a scoreboard for player data
 * <p>Uses {@link FastBoard} for packet based scoreboards</p>
 */
@SuppressWarnings("unused")
public class HealthBoard {

    // STATIC STUFF
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final Scoreboard MAIN = Bukkit.getScoreboardManager().getMainScoreboard();
    private static final Scoreboard DUMMY = Bukkit.getScoreboardManager().getNewScoreboard();

    // OBJECT STUFF
    private final Player player;
    private FastBoard fastBoard;
    private final Component[] lines = new Component[15];
    private final Component[] formats = new Component[15];
    private Component title;
    private boolean on;

    public HealthBoard(Player player) {
        this.player = player;
        this.on = false;
    }

    /**
     * Set the title of this Board
     *
     * @param title Title to set
     */
    public void setTitle(String title) {
        this.title = MINI_MESSAGE.deserialize(title);
    }

    /**
     * Set a specific line for this Board
     * <p>Lines 1 - 15</p>
     *
     * @param line Line to set (1 - 15)
     * @param text Text to put in line
     */
    public void setLine(int line, @Nullable String text) {
        setLine(line, text, null);
    }

    public void setLine(int line, @Nullable String text, String format) {
        Preconditions.checkArgument(line >= 1 && line <= 15, "Line number must be between 1 and 15, found: " + line);
        if (text != null) {
            Component component = MINI_MESSAGE.deserialize(text);
            this.lines[line - 1] = component;
        } else {
            this.lines[line - 1] = null;
        }
        if (format != null) {
            Component component = MINI_MESSAGE.deserialize(format);
            this.formats[line - 1] = component;
        } else {
            this.formats[line - 1] = null;
        }
    }

    /**
     * Delete a line in this Board
     * <p>Lines 1 - 15</p>
     *
     * @param line Line to delete (1 - 15)
     */
    public void deleteLine(int line) {
        setLine(line, null);
    }

    /**
     * Clear all lines of this Board
     */
    public void clearBoard() {
        for (int i = 1; i < 16; i++) {
            deleteLine(i);
        }
    }

    public void update() {
        if (this.fastBoard != null) {
            this.fastBoard.updateTitle(this.title);
            List<Component> lines = new ArrayList<>();
            List<Component> formats = new ArrayList<>();
            for (int i = 0; i < this.lines.length; i++) {
                if (this.lines[i] != null) {
                    lines.add(this.lines[i]);
                    if (this.formats[i] != null) {
                        formats.add(this.formats[i]);
                    } else {
                        formats.add(Component.empty());
                    }
                }
            }
            this.fastBoard.updateLines(lines, formats);
        }
    }

    /**
     * Toggle this Board on or off
     * <br>
     * When off, will not be visible to player, but can still update
     *
     * @param on Whether on or off
     */
    public void toggle(boolean on) {
        if (on) {
            this.fastBoard = new FastBoard(this.player);
            this.on = true;
        } else {
            if (this.fastBoard != null) {
                this.fastBoard.delete();
                this.fastBoard = null;
            }
            this.on = false;
            // Force resends the vanilla scoreboard
            this.player.setScoreboard(DUMMY);
            this.player.setScoreboard(MAIN);
        }
    }

    /**
     * Check if this Board is on or off
     *
     * @return True if on else false
     */
    public boolean isOn() {
        return this.on;
    }

}
