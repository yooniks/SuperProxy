// 
// Decompiled by Procyon v0.5.30
// 

package org.spacehq.opennbt.conversion.builtin;

import org.spacehq.opennbt.conversion.TagConverter;
import org.spacehq.opennbt.tag.builtin.ByteArrayTag;

public class ByteArrayTagConverter implements TagConverter<ByteArrayTag, byte[]> {

  @Override
  public byte[] convert(final ByteArrayTag tag) {
    return tag.getValue();
  }

  @Override
  public ByteArrayTag convert(final String name, final byte[] value) {
    return new ByteArrayTag(name, value);
  }
}
