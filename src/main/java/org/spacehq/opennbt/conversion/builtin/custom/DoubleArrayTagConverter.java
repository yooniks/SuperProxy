// 
// Decompiled by Procyon v0.5.30
// 

package org.spacehq.opennbt.conversion.builtin.custom;

import org.spacehq.opennbt.conversion.TagConverter;
import org.spacehq.opennbt.tag.builtin.custom.DoubleArrayTag;

public class DoubleArrayTagConverter implements TagConverter<DoubleArrayTag, double[]> {

  @Override
  public double[] convert(final DoubleArrayTag tag) {
    return tag.getValue();
  }

  @Override
  public DoubleArrayTag convert(final String name, final double[] value) {
    return new DoubleArrayTag(name, value);
  }
}
