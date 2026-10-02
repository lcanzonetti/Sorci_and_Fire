"""Mechanical 1.18 -> 1.21 GUI rewrite: PoseStack screen methods to GuiGraphics."""
import re, sys

def convert(s, container=True):
    # method signatures
    s = re.sub(r'(void (?:renderLabels|render|renderBg|renderButton|renderWidget|renderBackground|renderTooltip)\(\s*(?:@NotNull\s+)?)PoseStack(\s+\w+)', r'\1GuiGraphics\2', s)
    s = s.replace('void renderButton(', 'void renderWidget(')
    # font.draw / drawShadow
    def fdraw(m):
        font, shadow, gg, rest = m.group(1), m.group(2), m.group(3), m.group(4)
        return f'{gg}.drawString({font}, {rest}, {"true" if shadow else "false"});'
    s = re.sub(r'([\w.()]*font(?:renderer)?|this\.font|font)\.draw(Shadow)?\(\s*(\w+),\s*(.*?)\);', fdraw, s, flags=re.S)
    # blit with tracked texture
    out = []
    tex = None
    for line in s.split('\n'):
        m = re.search(r'RenderSystem\.setShaderTexture\(0,\s*(.+?)\);', line)
        if m:
            tex = m.group(1)
        m2 = re.search(r'this\.blit\((\w+),', line)
        if m2 and tex:
            line = line.replace(f'this.blit({m2.group(1)},', f'{m2.group(1)}.blit({tex},')
        out.append(line)
    s = '\n'.join(out)
    if container:
        s = re.sub(r'\n\s*this\.renderBackground\(\w+\);', '', s)
    s = re.sub(r'InventoryScreen\.renderEntityInInventory\(', 'IafGuiUtil.renderEntityInInventory(GUI_GRAPHICS, ', s)
    names = set(re.findall(r'GuiGraphics\s+(\w+)', s))
    for n in names:
        s = re.sub(r'\b' + n + r'\.(pushPose|popPose|translate|scale|mulPose|last)\(', n + r'.pose().\1(', s)
    s = s.replace('this.setBlitOffset(0);', '')
    s = s.replace('getMinecraft().getFrameTime()', 'getMinecraft().getTimer().getGameTimeDeltaPartialTick(true)')
    if 'import net.minecraft.client.gui.GuiGraphics;' not in s:
        s = s.replace('\nimport ', '\nimport net.minecraft.client.gui.GuiGraphics;\nimport ', 1)
    return s

if __name__ == '__main__':
    container = '--screen' not in sys.argv
    for p in [a for a in sys.argv[1:] if not a.startswith('--')]:
        s = open(p).read()
        open(p, 'w').write(convert(s, container))
