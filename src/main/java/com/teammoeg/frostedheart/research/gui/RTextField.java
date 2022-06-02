package com.teammoeg.frostedheart.research.gui;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.math.Bits;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.TextField;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.WidgetType;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;

public class RTextField extends Widget {

	public ITextComponent component = StringTextComponent.EMPTY;
	private ITextProperties[] formattedText = new ITextProperties[0];
	public int textFlags = 0;
	public int minWidth = 0;
	public int maxWidth = 5000;
	public int textSpacing = 10;
	public float scale = 1.0F;
	public Color4I textColor = Icon.EMPTY;
	public int maxLine=0;
	public RTextField(Panel panel) {
		super(panel);
	}

	public RTextField addFlags(int flags) {
		textFlags |= flags;
		return this;
	}

	public RTextField setMinWidth(int width) {
		minWidth = width;
		return this;
	}

	public RTextField setMaxWidth(int width) {
		maxWidth = width;
		return this;
	}
	public RTextField setMaxLine(int line) {
		maxLine = line;
		return this;
	}
	public RTextField setColor(Color4I color) {
		textColor = color;
		return this;
	}

	public RTextField setScale(float s) {
		scale = s;
		return this;
	}

	public RTextField setSpacing(int s) {
		textSpacing = s;
		return this;
	}


	public RTextField setText(String txt) {
		return setText(new StringTextComponent(txt));
	}
	public RTextField setText(ITextComponent txt) {
		Theme theme = getGui().getTheme();

		if (maxLine>0) {
			List<ITextProperties> ls=theme.listFormattedStringToWidth(new StringTextComponent("").appendSibling(txt), maxWidth);
			formattedText = ls.subList(0,Math.min(ls.size(), maxLine)).toArray(new ITextProperties[0]);
		} else {
			formattedText = theme.listFormattedStringToWidth(new StringTextComponent("").appendSibling(txt), maxWidth).toArray(new ITextProperties[0]);
		}

		return resize(theme);
	}
	public RTextField resize(Theme theme) {
		setWidth(0);

		for (ITextProperties s : formattedText) {
			setWidth(Math.max(width, (int) ((float) theme.getStringWidth(s) * scale)));
		}

		setWidth(MathHelper.clamp(width, minWidth, maxWidth));
		setHeight((int) ((Math.max(1, formattedText.length) * textSpacing - (textSpacing - theme.getFontHeight() + 1)) * scale));
		return this;
	}

	@Override
	public void addMouseOverText(TooltipList list) {
	}

	public void drawBackground(MatrixStack matrixStack, Theme theme, int x, int y, int w, int h) {
	}

	@Override
	public void draw(MatrixStack matrixStack, Theme theme, int x, int y, int w, int h) {
		drawBackground(matrixStack, theme, x, y, w, h);

		if (formattedText.length != 0) {
			boolean centered = Bits.getFlag(textFlags, 4);
			boolean centeredV = Bits.getFlag(textFlags, 32);
			Color4I col = textColor;
			if (col.isEmpty()) {
				col = theme.getContentColor(WidgetType.mouseOver(Bits.getFlag(textFlags, 16)));
			}

			int tx = x + (centered ? w / 2 : 0);
			int ty = y + (centeredV ? (h - theme.getFontHeight()) / 2 : 0);
			int i;
			if (scale == 1.0F) {
				for (i = 0; i < formattedText.length; ++i) {
					theme.drawString(matrixStack, formattedText[i], (float) tx, (float) (ty + i * textSpacing), col, textFlags);
				}
			} else {
				matrixStack.push();
				matrixStack.translate(tx, ty, 0.0D);
				matrixStack.scale(scale, scale, 1.0F);

				for (i = 0; i < formattedText.length; ++i) {
					theme.drawString(matrixStack, formattedText[i], 0.0F, (float) (i * textSpacing), col, textFlags);
				}

				matrixStack.pop();
			}
		}
	}
}