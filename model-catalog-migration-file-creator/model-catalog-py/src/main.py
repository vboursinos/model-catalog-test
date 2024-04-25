from pathlib import Path

from evoml_api_models import MlTask

from infos import get_model_infos
from models import ModelInfoList


def create_files():
    output: Path = Path("model_infos")
    output.mkdir(parents=True, exist_ok=True)
    for task in MlTask:
        model_infos: ModelInfoList = get_model_infos(task)
        (output / f"infos_{task.value}.json").write_text(model_infos.json(indent=2))


if __name__ == '__main__':
    create_files()