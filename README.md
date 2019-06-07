# EasyMOTD
Fabric mod for creating randomized server message of the day

Fabric: https://fabricmc.net/

# Configuration

Configuration is a json data file located at `easymotd:texts/motd.json`. The
json is a list of Texts in json form. Don't worry, a simple string can work.

You can force a line break by using `\n` in the text.

The actual file location should be at `world/datapacks/config/data/easymotd/texts/motd.json`

You will also need a pack.mcmeta file at `world/datapacks/config/pack.mcmeta`. See the data packs link below.

## Example
```json
[
  "Come play with us!\nBottom Text",
  "I see you",
  "If I were a rich man with a million or two, I'd live in a penthouse in a room with a view.",
  {"text": "I LOVE BEING PURPLE!", "color": "dark_purple"}
]
```

# Useful Links

- [Text Scheme](https://minecraft.gamepedia.com/Commands#Raw_JSON_text)
- [Json Generator](https://minecraftjson.com/)
- [Data Packs](https://minecraft.gamepedia.com/Tutorials/Creating_a_data_pack)
