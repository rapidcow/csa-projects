#!/usr/bin/env python3
from pathlib import Path

if __name__ == '__main__':
    content = Path(__file__).parent / 'swing-intro-content.tex'
    assert content.exists()
    lines = []
    with content.open('r', encoding='utf-8') as fp:
        i = 1
        for line in fp:
            if line.rstrip() == r'\end{minted}':
                i = -2
            if i == 0 and line[:1] == line[:1].lower():
                lines.append('\\noindent\n')
            lines.append(line)
            i += 1
    with content.open('w', encoding='utf-8') as fp:
        for line in lines:
            fp.write(line)
