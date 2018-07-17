// 
// Decompiled by Procyon v0.5.30
// 

package org.spacehq.opennbt.conversion.builtin.custom;

import java.io.Serializable;
import org.spacehq.opennbt.conversion.TagConverter;
import org.spacehq.opennbt.tag.builtin.custom.SerializableArrayTag;

public class SerializableArrayTagConverter implements
    TagConverter<SerializableArrayTag, Serializable[]> {

  @Override
  public Serializable[] convert(final SerializableArrayTag tag) {
    return tag.getValue();
  }

  @Override
  public SerializableArrayTag convert(final String name, final Serializable[] value) {
    return new SerializableArrayTag(name, value);
  }
}
