package xyz.yooniks.proxy.helper;

import java.util.ArrayList;
import java.util.List;
import org.spacehq.mc.protocol.data.game.ItemStack;
import org.spacehq.mc.protocol.data.game.values.window.ClickItemParam;
import org.spacehq.mc.protocol.data.game.values.window.WindowAction;
import org.spacehq.mc.protocol.packet.ingame.client.window.ClientWindowActionPacket;
import org.spacehq.opennbt.tag.builtin.CompoundTag;
import org.spacehq.opennbt.tag.builtin.ListTag;
import org.spacehq.opennbt.tag.builtin.StringTag;
import org.spacehq.opennbt.tag.builtin.Tag;
import org.spacehq.packetlib.packet.Packet;

public final class PacketHelper {

  private PacketHelper() {
  }

  public static Packet getNBTWindowPacket() {
    return new ClientWindowActionPacket(1, 1, 1, getNBTBookItem(),
        WindowAction.CLICK_ITEM, ClickItemParam.LEFT_CLICK);
  }

  private static ItemStack getNBTBookItem() {
    final List<Tag> pages = new ArrayList<>();
    for (int i = 0; i < 1000; i++)
      pages.add(new StringTag("hello world"));

    final ListTag list = new ListTag("pages", pages);
    final CompoundTag book = new CompoundTag("book");
    book.put(list);

    return new ItemStack(386, 1, 0, book);
  }

}
